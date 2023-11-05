package com.theraphy.backend.treatments.service;

import com.theraphy.backend.shared.exception.ResourceNotFoundException;
import com.theraphy.backend.shared.exception.ResourceValidationException;
import com.theraphy.backend.treatments.domain.model.entity.TreatmentPatient;
import com.theraphy.backend.treatments.domain.persistence.TreatmentPatientRepository;
import com.theraphy.backend.treatments.domain.service.TreatmentPatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class TreatmentPatientServiceImpl implements TreatmentPatientService {

    private static final String ENTITY = "Treatments_Patient";

    private final TreatmentPatientRepository  treatmentPatientRepository;

    private final Validator validator;


    public TreatmentPatientServiceImpl(TreatmentPatientRepository treatmentPatientRepository, Validator validator) {
        this.treatmentPatientRepository = treatmentPatientRepository;
        this.validator = validator;
    }

    @Override
    public List<TreatmentPatient> getAll() {
        return treatmentPatientRepository.findAll();
    }

    @Override
    public Page<TreatmentPatient> getAll(Pageable pageable) {
        return treatmentPatientRepository.findAll(pageable);
    }

    @Override
    public TreatmentPatient getById(Long treatmentPatientId) {
        return treatmentPatientRepository.findById(treatmentPatientId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, treatmentPatientId));
    }




    @Override
    public TreatmentPatient create(TreatmentPatient treatmentPatient) {
        Set<ConstraintViolation<TreatmentPatient>> violations = validator.validate(treatmentPatient);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return treatmentPatientRepository.save(treatmentPatient);
    }

    @Override
    public TreatmentPatient update(Long treatmentPatientId, TreatmentPatient request) {
        Set<ConstraintViolation<TreatmentPatient>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return treatmentPatientRepository.findById(treatmentPatientId).map(treatmentPatient ->
                        treatmentPatientRepository.save(
                                treatmentPatient.withPatient(request.getPatient())
                                        .withTreatment(request.getTreatment())
                                        .withRegistrationDate(request.getRegistrationDate())
                                        .withProgress(request.getProgress())))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, treatmentPatientId));
    }

    @Override
    public ResponseEntity<?> delete(Long treatmentPatientId) {
        return treatmentPatientRepository.findById(treatmentPatientId).map( treatmentPatient -> {
            treatmentPatientRepository.delete(treatmentPatient);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException(ENTITY,treatmentPatientId));
    }

    @Override
    public TreatmentPatient getByTreatmentId(Long treatmentId) {
        return treatmentPatientRepository.findByTreatmentId(treatmentId)
                .orElseThrow(()-> new ResourceNotFoundException("No Appointment with this name found for Patient"));

    }

   /* @Override
    public TreatmentPatient getByTitleAndPhysiotherapistId(String title, Long physiotherapistId) {
        return treatmentPatientRepository.findByTitleAndPhysiotherapistId(title, physiotherapistId).orElseThrow(()-> new ResourceNotFoundException("No Treatments with this title found for Physiotherapist"));
    }*/


}
