package com.theraphy.backend.profile.api.rest;

import com.theraphy.backend.profile.domain.service.PatientService;
import com.theraphy.backend.profile.mapping.PatientMapper;
import com.theraphy.backend.profile.resource.CreatePatientResource;
import com.theraphy.backend.profile.resource.PatientResource;
import com.theraphy.backend.profile.resource.UpdatePatientResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/patients", produces = "application/json")
@Tag(name = "Patients", description = "Create, read, update and delete patients")
public class PatientsController {
    private final PatientService patientService;

    private final PatientMapper mapper;

    public PatientsController(PatientService patientService, PatientMapper mapper) {
        this.patientService = patientService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<PatientResource> getAllPatients(Pageable pageable) {
        return mapper.modelListPage(patientService.getAll(), pageable);
    }

    @GetMapping("{patientId}")
    public PatientResource getPatientById(@PathVariable Long patientId) {
        return mapper.toResource(patientService.getById(patientId));
    }

    @GetMapping("userId={value}")
    public PatientResource getPatientByUserId(@PathVariable Long value) {
        return mapper.toResource(patientService.getByUserId(value));
    }

    @PostMapping
    public ResponseEntity<PatientResource> createPatient(@RequestBody CreatePatientResource resource) {
        return new ResponseEntity<>(mapper.toResource(patientService.create(mapper.toModel(resource))), HttpStatus.CREATED);
    }

    @PutMapping("{patientId}")
    public PatientResource updatePatient(@PathVariable Long patientId,
                                         @RequestBody UpdatePatientResource resource) {
        return mapper.toResource(patientService.update(patientId, mapper.toModel(resource)));
    }

    @DeleteMapping("{patientId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long patientId) {
        return patientService.delete(patientId);
    }
}