package com.lifttrack.api.infrastructure.rest.dto.mapper;

import com.lifttrack.api.domain.models.RoutineExercise;
import com.lifttrack.api.infrastructure.rest.dto.response.RoutineExerciseResponse;

public final class RoutineExerciseResponseMapper {

    private RoutineExerciseResponseMapper() {
    }

    public static RoutineExerciseResponse toResponse(RoutineExercise routineExercise) {
        return new RoutineExerciseResponse(
                routineExercise.uuid(),
                routineExercise.routineDayUuid(),
                routineExercise.exerciseUuid(),
                routineExercise.targetSets(),
                routineExercise.targetRepRange(),
                routineExercise.sortOrder(),
                routineExercise.notes(),
                routineExercise.createdAt());
    }
}
