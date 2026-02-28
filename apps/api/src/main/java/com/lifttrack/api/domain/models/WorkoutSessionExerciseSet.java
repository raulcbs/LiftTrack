package com.lifttrack.api.domain.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record WorkoutSessionExerciseSet(
        UUID uuid,
        UUID workoutSessionExerciseUuid,
        int setNumber,
        int reps,
        BigDecimal weight,
        Integer rir,
        boolean warmup,
        Instant createdAt) {

    public WorkoutSessionExerciseSet create(UUID workoutSessionExerciseUuid, int setNumber, int reps, BigDecimal weight,
            Integer rir, boolean warmup) {
        if (workoutSessionExerciseUuid == null) {
            throw new IllegalArgumentException("Workout session exercise UUID cannot be null");
        }
        if (setNumber <= 0) {
            throw new IllegalArgumentException("Set number must be greater than 0");
        }
        if (reps <= 0) {
            throw new IllegalArgumentException("Reps must be greater than 0");
        }
        if (weight.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        if (rir != null && rir < 0) {
            throw new IllegalArgumentException("RIR cannot be negative");
        }

        return new WorkoutSessionExerciseSet(null, workoutSessionExerciseUuid, setNumber, reps, weight, rir, warmup,
                null);
    }
}
