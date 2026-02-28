package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.util.UUID;

public record RoutineDay(
        UUID uuid,
        UUID routineUuid,
        int dayOfWeek,
        String name,
        int sortOrder,
        Instant createdAt) {

    public RoutineDay create(UUID routineUuid, int dayOfWeek, String name, int sortOrder) {
        if (routineUuid == null) {
            throw new IllegalArgumentException("Routine UUID cannot be null");
        }
        if (dayOfWeek < 1 || dayOfWeek > 7) {
            throw new IllegalArgumentException("Day of week must be between 1 and 7");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Routine day name cannot be null or blank");
        }
        if (sortOrder < 0) {
            throw new IllegalArgumentException("Sort order cannot be negative");
        }

        return new RoutineDay(null, routineUuid, dayOfWeek, name, sortOrder, null);
    }
}
