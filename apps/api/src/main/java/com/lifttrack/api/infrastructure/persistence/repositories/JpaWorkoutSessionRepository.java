package com.lifttrack.api.infrastructure.persistence.repositories;

import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaWorkoutSessionRepository extends JpaRepository<WorkoutSessionEntity, Long> {

    Optional<WorkoutSessionEntity> findByUuid(UUID uuid);

    List<WorkoutSessionEntity> findByUserUuidOrderBySessionDateDesc(UUID userUuid);

    List<WorkoutSessionEntity> findByUserUuidAndSessionDateBetweenOrderBySessionDateDesc(UUID userUuid, LocalDate from, LocalDate to);

    void deleteByUuid(UUID uuid);
}
