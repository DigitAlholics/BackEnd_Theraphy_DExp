package com.theraphy.backend.profile.service;

import com.theraphy.backend.profile.domain.model.entity.Patient;
import com.theraphy.backend.profile.domain.persistence.PatientRepository;
import com.theraphy.backend.profile.domain.service.PatientService;
import com.theraphy.backend.shared.exception.ResourceNotFoundException;
import com.theraphy.backend.shared.exception.ResourceValidationException;
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
public class PatientServiceImpl implements PatientService {

    private static final String ENTITY = "Patient";

    private final PatientRepository patientRepository;

    private final Validator validator;

    public PatientServiceImpl(PatientRepository patientRepository, Validator validator) {
        this.patientRepository = patientRepository;
        this.validator = validator;
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Override
    public Page<Patient> getAll(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    @Override
    public Patient getById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, patientId));
    }

    @Override
    public Patient getByUserId(Long userId) {
        return patientRepository.findByUserId(userId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, userId));
    }


    @Override
    public Patient create(Patient patient) {
        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        // Name Uniqueness validation
        Patient patientWithFirstName = patientRepository.findByFirstName(patient.getFirstName());

        if(patientWithFirstName != null)
            throw new ResourceValidationException(ENTITY,
                    "A patient with the same first name already exists.");

        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Long patientId, Patient request) {
        Set<ConstraintViolation<Patient>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return patientRepository.findById(patientId).map( patient ->
                        patientRepository.save(
                                patient.withFirstName(request.getFirstName()).
                                        withLastName(request.getLastName()).
                                        withAge(request.getAge()).
                                        withPhotoUrl(request.getPhotoUrl()).
                                        withBirthdayDate(request.getBirthdayDate()).
                                        withEmail(request.getEmail()).
                                        withAppointmentQuantity(request.getAppointmentQuantity())
                                ))
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY,patientId));
    }

    @Override
    public ResponseEntity<?> delete(Long patientId) {
        return patientRepository.findById(patientId).map(patient -> {
            patientRepository.delete(patient);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException(ENTITY,patientId));
    }

    @Override
    public Patient addAppointmentToPatient(Long patientId, String scheduledDate,String topic, String diagnosis, String done) {
        return patientRepository.findById(patientId).map(patient -> {
            return patientRepository.save(patient.addAppointment(scheduledDate,topic,diagnosis, done));
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY,patientId));
    }

    @Override
    public Patient addReviewToPatient(Long patientId, String description, Long stars) {
        return patientRepository.findById(patientId).map(patient -> {
            return patientRepository.save(patient.addReview(description, stars));
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY,patientId));
    }

    @Override
    public Patient addTreatmentToPatient(Long patientId, Date registrationDate, Double progress) {
        return patientRepository.findById(patientId).map(patient -> {
            return patientRepository.save(patient.addTreatmentPatient(registrationDate, progress));
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY,patientId));
    }


}