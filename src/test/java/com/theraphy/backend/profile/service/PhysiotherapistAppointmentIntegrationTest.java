package com.theraphy.backend.profile.service;

import com.theraphy.backend.appointments.domain.model.entity.Appointment;
import com.theraphy.backend.appointments.service.AppointmentServiceImpl;
import com.theraphy.backend.profile.domain.model.entity.Physiotherapist;
import com.theraphy.backend.profile.domain.persistence.PhysiotherapistRepository;
import com.theraphy.backend.shared.exception.ResourceValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PhysiotherapistAppointmentIntegrationTest {
    @InjectMocks
    private PhysiotherapistServiceImpl physiotherapistService;

    @Mock
    private PhysiotherapistRepository physiotherapistRepository;

    @Mock
    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testPhysiotherapistDailyAppointmentLimitExceeded() {
        // Configurar datos de prueba
        Long physiotherapistId = 1L;
        String scheduledDate = "2023-09-10";
        String topic = "Test Topic";
        String diagnosis = "Test Diagnosis";
        String done = "false";

        // Simular el comportamiento del repositorio para obtener al fisioterapeuta
        Physiotherapist physiotherapist = new Physiotherapist();
        physiotherapist.setConsultationsQuantity(6L); // Establecer el límite de citas diarias en 6
        when(physiotherapistRepository.findById(physiotherapistId)).thenReturn(Optional.of(physiotherapist));

        // Simular el comportamiento del servicio de citas para devolver 6 citas ya programadas para el mismo día
        List<Appointment> dailyAppointments = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Appointment appointment = new Appointment();
            appointment.setScheduledDate(scheduledDate);
            dailyAppointments.add(appointment);
        }
        when(appointmentService.getAppointmentsByPhysiotherapistAndDate(physiotherapistId, scheduledDate))
                .thenReturn(dailyAppointments);

        // Ejecutar el método que deseas probar
        Throwable exception = assertThrows(ResourceValidationException.class, () ->
                physiotherapistService.addAppointmentToPhysiotherapist(physiotherapistId, scheduledDate, topic, diagnosis, done)
        );

        // Verificar que se haya lanzado una excepción debido a que se excedió el límite
        assert(exception.getMessage().contains("Daily appointment limit exceeded"));
    }

}