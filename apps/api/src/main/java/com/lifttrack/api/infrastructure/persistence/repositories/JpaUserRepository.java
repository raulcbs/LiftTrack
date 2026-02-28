package com.lifttrack.api.infrastructure.persistence.repositories;

import com.lifttrack.api.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUuid(UUID uuid);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByUuid(UUID uuid);
}
