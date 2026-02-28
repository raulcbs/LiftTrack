package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.util.UUID;

public record WorkoutSessionExercise(
        UUID uuid,
        UUID workoutSessionUuid,
        UUID exerciseUuid,
        int sortOrder,
        String notes,
        Instant createdAt) {

    public WorkoutSessionExercise create(UUID workoutSessionUuid, UUID exerciseUuid, int sortOrder, String notes) {
        if (workoutSessionUuid == null) {
            throw new IllegalArgumentException("Workout session UUID cannot be null");
        }
        if (exerciseUuid == null) {
            throw new IllegalArgumentException("Exercise UUID cannot be null");
        }

        return new WorkoutSessionExercise(null, workoutSessionUuid, exerciseUuid, sortOrder, notes, null);
    }
}
