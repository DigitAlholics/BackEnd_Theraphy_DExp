package com.theraphy.backend.treatments.service;

import com.theraphy.backend.shared.exception.ResourceNotFoundException;
import com.theraphy.backend.shared.exception.ResourceValidationException;
import com.theraphy.backend.treatments.domain.model.entity.Treatment;
import com.theraphy.backend.treatments.domain.persistence.TreatmentRepository;
import com.theraphy.backend.treatments.domain.service.TreatmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TreatmentServiceImpl implements TreatmentService {
    private static final String ENTITY = "Treatment";

    private final TreatmentRepository treatmentRepository;

    private final Validator validator;

    public TreatmentServiceImpl(TreatmentRepository treatmentRepository, Validator validator) {
        this.treatmentRepository = treatmentRepository;
        this.validator = validator;
    }

    @Override
    public List<Treatment> getAll() {
        return treatmentRepository.findAll();
    }

    @Override
    public Page<Treatment> getAll(Pageable pageable) {
        return treatmentRepository.findAll(pageable);
    }

    @Override
    public Treatment getById(Long treatmentId) {
        return treatmentRepository.findById(treatmentId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, treatmentId));
    }

    @Override
    public Treatment create(Treatment treatment) {
        Set<ConstraintViolation<Treatment>> violations = validator.validate(treatment);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        Treatment treatmentWithTitle = treatmentRepository.findByTitle(treatment.getTitle());

        if(treatmentWithTitle != null)
            throw new ResourceValidationException(ENTITY,
                    "A Treatment with the same title already exists.");

        return treatmentRepository.save(treatment);
    }

    @Override
    public Treatment update(Long treatmentId, Treatment request) {
        Set<ConstraintViolation<Treatment>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return treatmentRepository.findById(treatmentId).map(treatment ->
                        treatmentRepository.save(
                                treatment.withTitle(request.getTitle())
                                        .withDescription(request.getDescription())
                                        .withPhotoUrl(request.getPhotoUrl())
                                        .withSessionsQuantity(request.getSessionsQuantity())))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, treatmentId));
    }

    @Override
    public ResponseEntity<?> delete(Long treatmentId) {
        return treatmentRepository.findById(treatmentId).map( treatment -> {
            treatmentRepository.delete(treatment);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException(ENTITY,treatmentId));
    }

    @Override
    public Treatment getByTitleAndPhysiotherapistId(String title, Long physiotherapistId) {
        return treatmentRepository.findByTitleAndPhysiotherapistId(title, physiotherapistId).orElseThrow(()-> new ResourceNotFoundException("No Treatments with this title found for Physiotherapist"));
    }

    @Override
    public Treatment addPatientToTreatment(Long treatmentId, Date registrationDate, Double progress) {
        return treatmentRepository.findById(treatmentId).map(patient -> {
            return treatmentRepository.save(patient.addTreatmentPatient(registrationDate, progress));
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY,treatmentId));    }
    }
