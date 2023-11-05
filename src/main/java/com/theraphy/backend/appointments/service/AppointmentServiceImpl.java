package com.theraphy.backend.appointments.service;

import com.theraphy.backend.appointments.domain.model.entity.Appointment;
import com.theraphy.backend.appointments.domain.persistence.AppointmentRepository;
import com.theraphy.backend.appointments.domain.service.AppointmentService;
import com.theraphy.backend.shared.exception.ResourceNotFoundException;
import com.theraphy.backend.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private static final String ENTITY = "Appointment";

    private final AppointmentRepository appointmentRepository;

    private final Validator validator;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, Validator validator) {
        this.appointmentRepository = appointmentRepository;
        this.validator = validator;
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Page<Appointment> getAll(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }

    @Override
    public Appointment getById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, appointmentId));
    }

    @Override
    public Appointment create(Appointment appointment) {
        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        Appointment appointmentWithTopic = appointmentRepository.findByTopic(appointment.getTopic());

        if(appointmentWithTopic != null)
            throw new ResourceValidationException(ENTITY,
                    "A Appointment with the same topic already exists.");

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Long appointmentId, Appointment request) {
        Set<ConstraintViolation<Appointment>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return appointmentRepository.findById(appointmentId).map(appointment ->
                        appointmentRepository.save(
                                appointment.withTopic(request.getTopic())
                                        .withDiagnosis(request.getDiagnosis())
                                        .withDone(request.getDone())
                                        .withScheduledDate(request.getScheduledDate())))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, appointmentId));
    }

    @Override
    public ResponseEntity<?> delete(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).map( appointment -> {
            appointmentRepository.delete(appointment);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException(ENTITY,appointmentId));
    }

    @Override
    public Appointment getByTopicAndPatientId(String topic, Long patientId) {
        return appointmentRepository.findByTopicAndPatientId(topic,patientId).orElseThrow(()-> new ResourceNotFoundException("No Appointment with this name found for Patient"));
    }

    @Override
    public Appointment getByTopicAndPhysiotherapistId(String topic, Long physiotherapistId) {
        return appointmentRepository.findByTopicAndPhysiotherapistId(topic, physiotherapistId).orElseThrow(()-> new ResourceNotFoundException("No Appointment with this name found for Physiotherapist"));
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = getById(appointmentId);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appointmentDate = LocalDateTime.parse(appointment.getScheduledDate());

        long hoursUntilAppointment = ChronoUnit.HOURS.between(now, appointmentDate);

        if(hoursUntilAppointment < 24){
            throw new ResourceValidationException(ENTITY, "You can only cancel an appointment 24 hours before it starts.");
        }else {
            delete(appointmentId);
        }
    }

    @Override
    public List<Appointment> getAppointmentsByPhysiotherapistAndDate(Long physiotherapistId, String date) {
        return appointmentRepository.findByPhysiotherapistIdAndScheduledDate(physiotherapistId, date);
    }



}
