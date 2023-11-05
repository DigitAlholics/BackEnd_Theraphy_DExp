package com.theraphy.backend.social.service;

import com.theraphy.backend.shared.exception.ResourceNotFoundException;
import com.theraphy.backend.shared.exception.ResourceValidationException;
import com.theraphy.backend.social.domain.model.entity.Review;
import com.theraphy.backend.social.domain.persistence.ReviewRepository;
import com.theraphy.backend.social.domain.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final String ENTITY = "Reviews";

    private final ReviewRepository reviewRepository;

    private final Validator validator;

    public ReviewServiceImpl(ReviewRepository reviewRepository, Validator validator) {
        this.reviewRepository = reviewRepository;
        this.validator = validator;
    }


    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Page<Review> getAll(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    @Override
    public Review getById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY, reviewId));
    }

    @Override
    public Review create(Review review) {
        Set<ConstraintViolation<Review>> violations = validator.validate(review);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return reviewRepository.save(review);
    }

    @Override
    public Review update(Long reviewId, Review request) {
        Set<ConstraintViolation<Review>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return reviewRepository.findById(reviewId).map(review ->
                        reviewRepository.save(
                                review.withDescription(request.getDescription())
                                                .withStars(request.getStars())))
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, reviewId));
    }

    @Override
    public ResponseEntity<?> delete(Long reviewId) {
        return reviewRepository.findById(reviewId).map( appointment -> {
            reviewRepository.delete(appointment);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFoundException(ENTITY,reviewId));    }

    @Override
    public Review getByStarsAndPatientId(Long stars, Long patientId) {
        return reviewRepository.findByStarsAndPatientId(stars,patientId).orElseThrow(()-> new ResourceNotFoundException("No Reviews with this stars quantity found for Patient"));
    }

    @Override
    public Review getByStarsAndPhysiotherapistId(Long stars, Long physiotherapistId) {
        return reviewRepository.findByStarsAndPhysiotherapistId(stars, physiotherapistId).orElseThrow(()-> new ResourceNotFoundException("No Reviews with this stars quantity found for Physiotherapist"));
    }


}
