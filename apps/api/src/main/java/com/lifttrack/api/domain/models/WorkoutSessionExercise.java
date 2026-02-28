package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.util.UUID;

public record WorkoutSessionExercise(
        UUID uuid,
        UUID workoutSessionUuid,
        UUID exerciseUuid,
        int sortOrder,
        String notes,
        Instant createdAt
) {
}
