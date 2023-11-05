package com.theraphy.backend.social.api.rest;

import com.theraphy.backend.profile.domain.service.PatientService;
import com.theraphy.backend.social.domain.service.ReviewService;
import com.theraphy.backend.social.mapping.ReviewMapper;
import com.theraphy.backend.social.resource.CreateReviewResource;
import com.theraphy.backend.social.resource.ReviewResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/patients/{patientId}/reviews", produces = "application/json")
@Tag(name = "Patient Reviews", description = "Create, read, update and delete reviews")
public class PatientReviewController {
    private final PatientService patientService;

    private final ReviewService reviewService;

    private final ReviewMapper mapper;

    public PatientReviewController(PatientService patientService, ReviewService reviewService, ReviewMapper mapper) {
        this.patientService = patientService;
        this.reviewService = reviewService;
        this.mapper = mapper;
    }


    @GetMapping
    public Page<ReviewResource> getAllReviewsByPatientId(@PathVariable Long patientId,
                                                              Pageable pageable) {
        return mapper.modelListPage(patientService.getById(patientId)
                .getReviews().stream().toList(), pageable);
    }

    @PostMapping
    public ReviewResource createReview(@PathVariable Long patientId,
                                                 @RequestBody CreateReviewResource resource) {

        patientService.addReviewToPatient(patientId, resource.getDescription(), resource.getStars());
        return mapper.toResource(reviewService
                .getByStarsAndPatientId(resource.getStars(), patientId));
    }
}
