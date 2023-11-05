package com.theraphy.backend.profile.domain.persistence;

import com.theraphy.backend.profile.domain.model.entity.Physiotherapist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysiotherapistRepository extends JpaRepository<Physiotherapist, Long> {
    Physiotherapist findByFirstName(String firstName);
    Optional<Physiotherapist> findByUserId(Long userId);
}