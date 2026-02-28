package com.lifttrack.api.domain.ports;

import com.lifttrack.api.domain.models.WorkoutSessionExerciseSet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutSessionExerciseSetRepository {

    WorkoutSessionExerciseSet save(WorkoutSessionExerciseSet set);

    Optional<WorkoutSessionExerciseSet> findByUuid(UUID uuid);

    List<WorkoutSessionExerciseSet> findByWorkoutSessionExerciseUuid(UUID workoutSessionExerciseUuid);

    Optional<WorkoutSessionExerciseSet> findLastByUserUuidAndExerciseUuid(UUID userUuid, UUID exerciseUuid);

    void deleteByUuid(UUID uuid);
}
