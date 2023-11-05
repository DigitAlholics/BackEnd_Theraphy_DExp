package com.theraphy.backend.appointments.api.rest;

import com.theraphy.backend.appointments.domain.service.AppointmentService;
import com.theraphy.backend.appointments.mapping.AppointmentMapper;
import com.theraphy.backend.appointments.resource.AppointmentResource;
import com.theraphy.backend.appointments.resource.CreateAppointmentResource;
import com.theraphy.backend.profile.domain.service.PhysiotherapistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/physiotherapists/{physiotherapistId}/appointments", produces = "application/json")
@Tag(name = "Physiotherapist Appointments", description = "Create, read, update and delete appointments")
public class PhysiotherapistAppointmentsController {

    private final PhysiotherapistService physiotherapistService;

    private final AppointmentService appointmentService;

    private final AppointmentMapper mapper;

    public PhysiotherapistAppointmentsController(PhysiotherapistService physiotherapistService, AppointmentService appointmentService, AppointmentMapper mapper) {
        this.physiotherapistService = physiotherapistService;
        this.appointmentService = appointmentService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<AppointmentResource> getAllAppointmentsByPhysiotherapistId(@PathVariable Long physiotherapistId,
                                                                   Pageable pageable) {
        return mapper.modelListPage(physiotherapistService.getById(physiotherapistId)
                .getAppointments().stream().toList(), pageable);
    }

    @PostMapping
    public AppointmentResource createAppointment(@PathVariable Long physiotherapistId,
                                                 @RequestBody CreateAppointmentResource resource) {

        physiotherapistService.addAppointmentToPhysiotherapist(physiotherapistId, resource.getScheduledDate(),resource.getTopic(), resource.getDiagnosis(), resource.getDone());
        return mapper.toResource(appointmentService
                .getByTopicAndPatientId(resource.getTopic(), physiotherapistId));
    }
}
