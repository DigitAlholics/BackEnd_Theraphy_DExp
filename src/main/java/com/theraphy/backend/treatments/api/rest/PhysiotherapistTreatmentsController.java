package com.theraphy.backend.treatments.api.rest;

import com.theraphy.backend.profile.domain.service.PhysiotherapistService;
import com.theraphy.backend.treatments.domain.service.TreatmentService;
import com.theraphy.backend.treatments.mapping.TreatmentMapper;
import com.theraphy.backend.treatments.resource.CreateTreatmentResource;
import com.theraphy.backend.treatments.resource.TreatmentResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/physiotherapists/{physiotherapistId}/treatments", produces = "application/json")
@Tag(name = "Physiotherapist Treatments", description = "Create, read, update and delete treatments")
public class PhysiotherapistTreatmentsController {
    private final PhysiotherapistService physiotherapistService;

    private final TreatmentService treatmentService;

    private final TreatmentMapper mapper;

    public PhysiotherapistTreatmentsController(PhysiotherapistService physiotherapistService, TreatmentService treatmentService, TreatmentMapper mapper) {
        this.physiotherapistService = physiotherapistService;
        this.treatmentService = treatmentService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<TreatmentResource> getAllTreatmentsByPhysiotherapistId(@PathVariable Long physiotherapistId,
                                                                    Pageable pageable) {
        return mapper.modelListPage(physiotherapistService.getById(physiotherapistId)
                .getTreatments().stream().toList(), pageable);
    }

    @PostMapping
    public TreatmentResource createTreatment(@PathVariable Long physiotherapistId,
                                            @RequestBody CreateTreatmentResource resource) {

        physiotherapistService.addTreatmentToPhysiotherapist(physiotherapistId, resource.getTitle(), resource.getDescription(), resource.getPhotoUrl(), resource.getSessionsQuantity());
        return mapper.toResource(treatmentService
                .getByTitleAndPhysiotherapistId(resource.getTitle(), physiotherapistId));
    }
}
