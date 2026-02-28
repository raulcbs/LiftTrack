package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.util.UUID;

public record RoutineExercise(
        UUID uuid,
        UUID routineDayUuid,
        UUID exerciseUuid,
        Integer targetSets,
        String targetRepRange,
        int sortOrder,
        String notes,
        Instant createdAt) {

    public RoutineExercise create(UUID routineDayUuid, UUID exerciseUuid, Integer targetSets, String targetRepRange,
            int sortOrder, String notes) {
        if (routineDayUuid == null) {
            throw new IllegalArgumentException("Routine day UUID cannot be null");
        }
        if (exerciseUuid == null) {
            throw new IllegalArgumentException("Exercise UUID cannot be null");
        }
        if (targetSets != null && targetSets < 0) {
            throw new IllegalArgumentException("Target sets cannot be negative");
        }
        if (targetRepRange != null && targetRepRange.isBlank()) {
            throw new IllegalArgumentException("Target rep range cannot be blank");
        }
        if (sortOrder < 0) {
            throw new IllegalArgumentException("Sort order cannot be negative");
        }

        return new RoutineExercise(null, routineDayUuid, exerciseUuid, targetSets, targetRepRange, sortOrder, notes, null);
    }
}
