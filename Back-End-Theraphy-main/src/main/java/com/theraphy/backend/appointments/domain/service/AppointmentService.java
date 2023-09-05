package com.theraphy.backend.appointments.domain.service;

import com.theraphy.backend.appointments.domain.model.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AppointmentService {

    List<Appointment> getAll();
    Page<Appointment> getAll(Pageable pageable);
    Appointment getById(Long appointmentId);
    Appointment create(Appointment appointment);
    Appointment update(Long appointmentId, Appointment request);
    ResponseEntity<?> delete(Long appointmentId);
    Appointment getByTopicAndPatientId(String topic, Long patientId);
    Appointment getByTopicAndPhysiotherapistId(String topic, Long physiotherapistId);

    void cancelAppointment(Long appointmentId);
}