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
        Instant createdAt) {

    public WorkoutSession create(UUID userUuid, UUID routineDayUuid, LocalDate sessionDate, Instant startedAt,
            Instant finishedAt, String notes) {
        if (userUuid == null) {
            throw new IllegalArgumentException("User UUID cannot be null");
        }
        if (sessionDate == null) {
            throw new IllegalArgumentException("Session date cannot be null");
        }

        return new WorkoutSession(null, userUuid, routineDayUuid, sessionDate, startedAt, finishedAt, notes, null);
    }
}
