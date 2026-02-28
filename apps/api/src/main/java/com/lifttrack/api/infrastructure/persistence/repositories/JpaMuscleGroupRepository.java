package com.lifttrack.api.infrastructure.persistence.repositories;

import com.lifttrack.api.infrastructure.persistence.entities.MuscleGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaMuscleGroupRepository extends JpaRepository<MuscleGroupEntity, Long> {

    Optional<MuscleGroupEntity> findByUuid(UUID uuid);
}
