package com.lifttrack.api.infrastructure.rest.dto.mapper;

import com.lifttrack.api.domain.models.RoutineDay;
import com.lifttrack.api.infrastructure.rest.dto.response.RoutineDayResponse;

public final class RoutineDayResponseMapper {

    private RoutineDayResponseMapper() {
    }

    public static RoutineDayResponse toResponse(RoutineDay routineDay) {
        return new RoutineDayResponse(
                routineDay.uuid(),
                routineDay.routineUuid(),
                routineDay.dayOfWeek(),
                routineDay.name(),
                routineDay.sortOrder(),
                routineDay.createdAt());
    }
}
