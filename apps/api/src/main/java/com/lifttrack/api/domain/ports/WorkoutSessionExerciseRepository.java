package com.lifttrack.api.domain.ports;

import com.lifttrack.api.domain.models.WorkoutSessionExercise;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutSessionExerciseRepository {

    WorkoutSessionExercise save(WorkoutSessionExercise workoutSessionExercise);

    Optional<WorkoutSessionExercise> findByUuid(UUID uuid);

    List<WorkoutSessionExercise> findByWorkoutSessionUuid(UUID workoutSessionUuid);

    void deleteByUuid(UUID uuid);
}
