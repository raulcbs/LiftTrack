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
        Instant createdAt) {

    public PersonalRecord create(UUID userUuid, UUID exerciseUuid, String recordType, BigDecimal weight, Integer reps,
            LocalDate achievedAt) {
        if (userUuid == null) {
            throw new IllegalArgumentException("User UUID cannot be null");
        }
        if (exerciseUuid == null) {
            throw new IllegalArgumentException("Exercise UUID cannot be null");
        }
        if (recordType == null || recordType.isBlank()) {
            throw new IllegalArgumentException("Record type cannot be null or blank");
        }
        if (achievedAt == null) {
            throw new IllegalArgumentException("Achieved at date cannot be null");
        }

        return new PersonalRecord(null, userUuid, exerciseUuid, recordType, weight, reps, achievedAt, null, null);
    }

}
