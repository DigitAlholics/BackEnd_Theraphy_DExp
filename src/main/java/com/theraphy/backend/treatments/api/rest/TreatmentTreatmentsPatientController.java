package com.theraphy.backend.treatments.api.rest;


import com.theraphy.backend.treatments.domain.service.TreatmentPatientService;
import com.theraphy.backend.treatments.domain.service.TreatmentService;
import com.theraphy.backend.treatments.mapping.TreatmentPatientMapper;
import com.theraphy.backend.treatments.resource.CreateTreatmentPatientResource;
import com.theraphy.backend.treatments.resource.TreatmentPatientResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/treatments/{treatmentPatientId}/treatments_by_patient", produces = "application/json")
@Tag(name = "Treatments TreatmentsPatients", description = "Create, read, update and delete treatments")
public class TreatmentTreatmentsPatientController {

    private final TreatmentService treatmentService;

    private final TreatmentPatientService treatmentPatientService;

    private final TreatmentPatientMapper mapper;


    public TreatmentTreatmentsPatientController(TreatmentService treatmentsService, TreatmentPatientService treatmentService, TreatmentPatientMapper mapper) {
        this.treatmentService = treatmentsService;
        this.treatmentPatientService = treatmentService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<TreatmentPatientResource> getAllTreatmentsPatientByTreatmentId(@PathVariable Long treatmentId,
                                                                                       Pageable pageable) {
        return mapper.modelListPage(treatmentService.getById(treatmentId)
                .getTreatments().stream().toList(), pageable);
    }

    @PostMapping
    public TreatmentPatientResource createTreatmentPatient(@PathVariable Long treatmentId,
                                                     @RequestBody CreateTreatmentPatientResource resource) {

        treatmentService.addPatientToTreatment(treatmentId, resource.getRegistrationDate(), resource.getProgress());
        return mapper.toResource(treatmentPatientService
                .getById(treatmentId));
    }

}
