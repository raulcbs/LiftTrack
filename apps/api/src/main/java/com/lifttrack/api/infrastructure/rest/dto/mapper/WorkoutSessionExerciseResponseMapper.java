package com.lifttrack.api.infrastructure.rest.dto.mapper;

import com.lifttrack.api.domain.models.WorkoutSessionExercise;
import com.lifttrack.api.infrastructure.rest.dto.response.WorkoutSessionExerciseResponse;

public final class WorkoutSessionExerciseResponseMapper {

    private WorkoutSessionExerciseResponseMapper() {
    }

    public static WorkoutSessionExerciseResponse toResponse(WorkoutSessionExercise workoutSessionExercise) {
        return new WorkoutSessionExerciseResponse(
                workoutSessionExercise.uuid(),
                workoutSessionExercise.workoutSessionUuid(),
                workoutSessionExercise.exerciseUuid(),
                workoutSessionExercise.sortOrder(),
                workoutSessionExercise.notes(),
                workoutSessionExercise.createdAt());
    }
}
