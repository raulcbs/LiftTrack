package com.lifttrack.api.infrastructure.rest.dto.mapper;

import com.lifttrack.api.domain.models.Exercise;
import com.lifttrack.api.infrastructure.rest.dto.response.ExerciseResponse;

public final class ExerciseResponseMapper {

    private ExerciseResponseMapper() {
    }

    public static ExerciseResponse toResponse(Exercise exercise) {
        return new ExerciseResponse(
                exercise.uuid(),
                exercise.name(),
                exercise.muscleGroupUuid(),
                exercise.userUuid(),
                exercise.createdAt());
    }
}
