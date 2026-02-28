package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.util.UUID;

public record RoutineDay(
        UUID uuid,
        UUID routineUuid,
        int dayOfWeek,
        String name,
        int sortOrder,
        Instant createdAt
) {
}
