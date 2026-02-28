package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record WorkoutSession(
        UUID uuid,
        UUID userUuid,
        UUID routineDayUuid,
        LocalDate sessionDate,
        Instant startedAt,
        Instant finishedAt,
        String notes,
        Instant createdAt
) {
}
