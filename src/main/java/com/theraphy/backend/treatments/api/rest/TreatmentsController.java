package com.theraphy.backend.treatments.api.rest;

import com.theraphy.backend.treatments.domain.service.TreatmentService;
import com.theraphy.backend.treatments.mapping.TreatmentMapper;
import com.theraphy.backend.treatments.resource.CreateTreatmentResource;
import com.theraphy.backend.treatments.resource.TreatmentResource;
import com.theraphy.backend.treatments.resource.UpdateTreatmentResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/treatments", produces = "application/json")
@Tag(name = "Treatments", description = "Create, read, update and delete treatments")
public class TreatmentsController {
    private final TreatmentService treatmentService;

    private final TreatmentMapper mapper;

    public TreatmentsController(TreatmentService treatmentService, TreatmentMapper mapper) {
        this.treatmentService = treatmentService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<TreatmentResource> getAllTreatments(Pageable pageable) {
        return mapper.modelListPage(treatmentService.getAll(), pageable);
    }

    @GetMapping("{treatmentId}")
    public TreatmentResource getTreatmentById(@PathVariable Long treatmentId) {
        return mapper.toResource(treatmentService.getById(treatmentId));
    }

    @PostMapping
    public ResponseEntity<TreatmentResource> createTreatment(@RequestBody CreateTreatmentResource resource) {
        return new ResponseEntity<>(mapper.toResource(treatmentService.create(mapper.toModel(resource))), HttpStatus.CREATED);
    }

    @PutMapping("{treatmentId}")
    public TreatmentResource updateTreatment(@PathVariable Long treatmentId,
                                             @RequestBody UpdateTreatmentResource resource) {
        return mapper.toResource(treatmentService.update(treatmentId, mapper.toModel(resource)));
    }

    @DeleteMapping("{treatmentId}")
    public ResponseEntity<?> deleteTreatment(@PathVariable Long treatmentId) {
        return treatmentService.delete(treatmentId);
    }

}