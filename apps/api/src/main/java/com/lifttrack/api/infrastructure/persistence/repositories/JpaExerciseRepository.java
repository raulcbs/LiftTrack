package com.lifttrack.api.infrastructure.persistence.repositories;

import com.lifttrack.api.infrastructure.persistence.entities.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

    Optional<ExerciseEntity> findByUuid(UUID uuid);

    List<ExerciseEntity> findByMuscleGroupUuid(UUID muscleGroupUuid);

    List<ExerciseEntity> findByUserIsNull();

    List<ExerciseEntity> findByUserUuid(UUID userUuid);

    void deleteByUuid(UUID uuid);
}
