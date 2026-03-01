package com.lifttrack.api.infrastructure.rest.dto.mapper;

import com.lifttrack.api.domain.models.Routine;
import com.lifttrack.api.infrastructure.rest.dto.response.RoutineResponse;

public final class RoutineResponseMapper {

    private RoutineResponseMapper() {
    }

    public static RoutineResponse toResponse(Routine routine) {
        return new RoutineResponse(
                routine.uuid(),
                routine.userUuid(),
                routine.name(),
                routine.description(),
                routine.active(),
                routine.createdAt(),
                routine.updatedAt());
    }
}
