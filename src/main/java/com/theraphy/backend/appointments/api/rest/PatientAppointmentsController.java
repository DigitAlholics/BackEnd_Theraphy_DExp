package com.theraphy.backend.appointments.api.rest;

import com.theraphy.backend.appointments.domain.service.AppointmentService;
import com.theraphy.backend.appointments.mapping.AppointmentMapper;
import com.theraphy.backend.appointments.resource.AppointmentResource;
import com.theraphy.backend.appointments.resource.CreateAppointmentResource;
import com.theraphy.backend.profile.domain.service.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/patients/{patientId}/appointments", produces = "application/json")
@Tag(name = "Patient Appointments", description = "Create, read, update and delete appointments")
public class PatientAppointmentsController {

    private final PatientService patientService;

    private final AppointmentService appointmentService;

    private final AppointmentMapper mapper;

    public PatientAppointmentsController(PatientService patientService, AppointmentService appointmentService, AppointmentMapper mapper) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<AppointmentResource> getAllAppointmentsByPatientId(@PathVariable Long patientId,
                                                             Pageable pageable) {
        return mapper.modelListPage(patientService.getById(patientId)
                .getAppointments().stream().toList(), pageable);
    }

    @PostMapping
    public AppointmentResource createAppointment(@PathVariable Long patientId,
                                             @RequestBody CreateAppointmentResource resource) {

        patientService.addAppointmentToPatient(patientId, resource.getScheduledDate(),resource.getTopic(), resource.getDiagnosis(), resource.getDone());
        return mapper.toResource(appointmentService
                .getByTopicAndPatientId(resource.getTopic(), patientId));
    }
}
