package com.lifttrack.api.infrastructure.persistence.repositories;

import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaWorkoutSessionExerciseRepository extends JpaRepository<WorkoutSessionExerciseEntity, Long> {
    Optional<WorkoutSessionExerciseEntity> findByUuid(UUID uuid);

    List<WorkoutSessionExerciseEntity> findByWorkoutSessionUuidOrderBySortOrder(UUID workoutSessionUuid);

    void deleteByUuid(UUID uuid);
}
