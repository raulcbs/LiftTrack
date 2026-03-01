package com.lifttrack.api.infrastructure.rest.dto.mapper;

import com.lifttrack.api.domain.models.WorkoutSession;
import com.lifttrack.api.infrastructure.rest.dto.response.WorkoutSessionResponse;

public final class WorkoutSessionResponseMapper {

    private WorkoutSessionResponseMapper() {
    }

    public static WorkoutSessionResponse toResponse(WorkoutSession workoutSession) {
        return new WorkoutSessionResponse(
                workoutSession.uuid(),
                workoutSession.userUuid(),
                workoutSession.routineDayUuid(),
                workoutSession.sessionDate(),
                workoutSession.startedAt(),
                workoutSession.finishedAt(),
                workoutSession.notes(),
                workoutSession.createdAt());
    }
}
