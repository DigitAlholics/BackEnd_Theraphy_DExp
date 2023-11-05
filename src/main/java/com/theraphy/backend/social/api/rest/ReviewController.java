package com.theraphy.backend.social.api.rest;

import com.theraphy.backend.social.domain.service.ReviewService;
import com.theraphy.backend.social.mapping.ReviewMapper;
import com.theraphy.backend.social.resource.CreateReviewResource;
import com.theraphy.backend.social.resource.ReviewResource;
import com.theraphy.backend.social.resource.UpdateReviewResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/reviews", produces = "application/json")
@Tag(name = "Reviews", description = "Create, read, update and delete reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper mapper;

    public ReviewController(ReviewService reviewService, ReviewMapper mapper) {
        this.reviewService = reviewService;
        this.mapper = mapper;
    }


    @GetMapping
    public Page<ReviewResource> getAllReviews(Pageable pageable) {
        return mapper.modelListPage(reviewService.getAll(), pageable);
    }

    @GetMapping("{reviewId}")
    public ReviewResource getReviewById(@PathVariable Long reviewId) {
        return mapper.toResource(reviewService.getById(reviewId));
    }




    @PostMapping
    public ResponseEntity<ReviewResource> createReview(@RequestBody CreateReviewResource resource) {
        return new ResponseEntity<>(mapper.toResource(reviewService.create(mapper.toModel(resource))), HttpStatus.CREATED);
    }

    @PutMapping("{reviewId}")
    public ReviewResource updateReview(@PathVariable Long reviewId,
                                                 @RequestBody UpdateReviewResource resource) {
        return mapper.toResource(reviewService.update(reviewId, mapper.toModel(resource)));
    }

    @DeleteMapping("{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        return reviewService.delete(reviewId);
    }
}
