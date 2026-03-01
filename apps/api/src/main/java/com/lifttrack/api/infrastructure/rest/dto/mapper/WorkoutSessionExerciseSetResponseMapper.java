package com.lifttrack.api.infrastructure.rest.dto.mapper;

import com.lifttrack.api.domain.models.WorkoutSessionExerciseSet;
import com.lifttrack.api.infrastructure.rest.dto.response.WorkoutSessionExerciseSetResponse;

public final class WorkoutSessionExerciseSetResponseMapper {

    private WorkoutSessionExerciseSetResponseMapper() {
    }

    public static WorkoutSessionExerciseSetResponse toResponse(WorkoutSessionExerciseSet set) {
        return new WorkoutSessionExerciseSetResponse(
                set.uuid(),
                set.workoutSessionExerciseUuid(),
                set.setNumber(),
                set.reps(),
                set.weight(),
                set.rir(),
                set.warmup(),
                set.createdAt());
    }
}
