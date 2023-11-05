package com.theraphy.backend.security.domain.persistence;

import com.theraphy.backend.security.domain.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
