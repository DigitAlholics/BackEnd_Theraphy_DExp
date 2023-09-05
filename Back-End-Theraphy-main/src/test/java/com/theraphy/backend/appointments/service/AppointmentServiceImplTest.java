package com.theraphy.backend.appointments.service;

import com.theraphy.backend.appointments.domain.model.entity.Appointment;
import com.theraphy.backend.appointments.domain.persistence.AppointmentRepository;
import com.theraphy.backend.shared.exception.ResourceNotFoundException;
import com.theraphy.backend.shared.exception.ResourceValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        appointmentService = new AppointmentServiceImpl(appointmentRepository, null );
    }
    @Test
    public void testCancelAppointmentBefore24Hours() {
        LocalDateTime appointmentDateTime = LocalDateTime.now().plusHours(25);
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setScheduledDate(appointmentDateTime.toString());
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        assertDoesNotThrow(() -> {
            appointmentService.cancelAppointment(1L);
        });
        verify(appointmentRepository).delete(appointment);
    }
    @Test
    public void testCancelAppointmentWithin24Hours() {
        LocalDateTime appointmentDateTime = LocalDateTime.now().plusHours(15);
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setScheduledDate(appointmentDateTime.toString());
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        assertThrows(ResourceValidationException.class, () -> {
            appointmentService.cancelAppointment(1L);
        });
        verify(appointmentRepository, never()).delete(any());
    }
    @Test
    public void testCancelNonexistentAppointment() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            appointmentService.cancelAppointment(1L);
        } catch (ResourceNotFoundException e) {
            verify(appointmentRepository, never()).delete(any());
        }
    }
}