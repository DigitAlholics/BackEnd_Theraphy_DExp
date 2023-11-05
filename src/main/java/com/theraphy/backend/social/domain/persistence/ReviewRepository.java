package com.theraphy.backend.social.domain.persistence;

import com.theraphy.backend.social.domain.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByStarsAndPatientId(Long stars, Long patientId);
    Optional<Review> findByStarsAndPhysiotherapistId(Long stars, Long physiotherapistId);
    Review findByStars(Long stars);

}
