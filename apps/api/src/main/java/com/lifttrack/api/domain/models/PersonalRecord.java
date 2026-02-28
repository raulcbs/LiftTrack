package com.lifttrack.api.domain.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record PersonalRecord(
        UUID uuid,
        UUID userUuid,
        UUID exerciseUuid,
        String recordType,
        BigDecimal weight,
        Integer reps,
        LocalDate achievedAt,
        UUID workoutSessionExerciseSetUuid,
        Instant createdAt
) {
}
