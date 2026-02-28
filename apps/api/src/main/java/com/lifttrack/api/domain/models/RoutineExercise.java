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
        Instant createdAt
) {
}
