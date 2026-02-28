package com.lifttrack.api.domain.ports;

import com.lifttrack.api.domain.models.Exercise;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository {

    Exercise save(Exercise exercise);

    Optional<Exercise> findByUuid(UUID uuid);

    List<Exercise> findByMuscleGroupUuid(UUID muscleGroupUuid);

    List<Exercise> findGlobalExercises();

    List<Exercise> findByUserUuid(UUID userUuid);

    void deleteByUuid(UUID uuid);
}
