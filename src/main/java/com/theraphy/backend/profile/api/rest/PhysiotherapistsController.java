package com.theraphy.backend.profile.api.rest;

import com.theraphy.backend.profile.domain.service.PhysiotherapistService;
import com.theraphy.backend.profile.mapping.PhysiotherapistMapper;
import com.theraphy.backend.profile.resource.CreatePhysiotherapistResource;
import com.theraphy.backend.profile.resource.PhysiotherapistResource;
import com.theraphy.backend.profile.resource.UpdatePhysiotherapistResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/physiotherapists", produces = "application/json")
@Tag(name = "Physiotherapists", description = "Create, read, update and delete physiotherapists")
public class PhysiotherapistsController {
    private final PhysiotherapistService physiotherapistService;

    private final PhysiotherapistMapper mapper;

    public PhysiotherapistsController(PhysiotherapistService physiotherapistService, PhysiotherapistMapper mapper) {
        this.physiotherapistService = physiotherapistService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<PhysiotherapistResource> getAllPhysiotherapists(Pageable pageable) {
        return mapper.modelListPage(physiotherapistService.getAll(), pageable);
    }

    @GetMapping("{physiotherapistId}")
    public PhysiotherapistResource getPhysiotherapistById(@PathVariable Long physiotherapistId) {
        return mapper.toResource(physiotherapistService.getById(physiotherapistId));
    }

    @GetMapping("userId={value}")
    public PhysiotherapistResource getPhysiotherapistByUserId(@PathVariable Long value) {
        return mapper.toResource(physiotherapistService.getByUserId(value));
    }

    @PostMapping
    public ResponseEntity<PhysiotherapistResource> createPhysiotherapist(@RequestBody CreatePhysiotherapistResource resource) {
        return new ResponseEntity<>(mapper.toResource(physiotherapistService.create(mapper.toModel(resource))), HttpStatus.CREATED);
    }

    @PutMapping("{physiotherapistId}")
    public PhysiotherapistResource updatePhysiotherapist(@PathVariable Long physiotherapistId,
                                                         @RequestBody UpdatePhysiotherapistResource resource) {
        return mapper.toResource(physiotherapistService.update(physiotherapistId, mapper.toModel(resource)));
    }

    @DeleteMapping("{physiotherapistId}")
    public ResponseEntity<?> deletePhysiotherapist(@PathVariable Long physiotherapistId) {
        return physiotherapistService.delete(physiotherapistId);
    }

}
