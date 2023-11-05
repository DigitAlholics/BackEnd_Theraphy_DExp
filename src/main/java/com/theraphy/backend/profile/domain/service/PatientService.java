package com.theraphy.backend.profile.domain.service;

import com.theraphy.backend.profile.domain.model.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface PatientService {
    List<Patient> getAll();
    Page<Patient> getAll(Pageable pageable);
    Patient getById(Long patientId);

    Patient getByUserId(Long userId);
    Patient create(Patient patient);
    Patient update(Long patientId, Patient request);
    ResponseEntity<?> delete(Long patientId);
    Patient addAppointmentToPatient(Long patientId, String scheduledDate,String topic, String diagnosis, String done);

    Patient addReviewToPatient(Long patientId, String description, Long stars);

    Patient addTreatmentToPatient(Long patientId, Date registrationDate, Double progress);

}