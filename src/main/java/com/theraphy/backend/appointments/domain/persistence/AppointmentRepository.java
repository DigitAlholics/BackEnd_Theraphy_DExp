package com.theraphy.backend.appointments.domain.persistence;

import com.theraphy.backend.appointments.domain.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    Optional<Appointment> findByTopicAndPatientId(String topic, Long patientId);
    Appointment findByTopic(String topic);
    Optional<Appointment> findByTopicAndPhysiotherapistId(String topic, Long physiotherapistId);
    List<Appointment> findByPhysiotherapistIdAndScheduledDate(Long physiotherapistId, String date);
}