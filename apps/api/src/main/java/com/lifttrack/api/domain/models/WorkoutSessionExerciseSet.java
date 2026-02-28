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
        Instant createdAt
) {
}
