package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Personal record details")
public record PersonalRecordResponse(
        @Schema(description = "Unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID uuid,

        @Schema(description = "UUID of the user who achieved this record", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID userUuid,

        @Schema(description = "UUID of the exercise", example = "550e8400-e29b-41d4-a716-446655440002")
        UUID exerciseUuid,

        @Schema(description = "Type of personal record", example = "ONE_REP_MAX")
        String recordType,

        @Schema(description = "Weight achieved in kilograms", example = "120.0")
        BigDecimal weight,

        @Schema(description = "Number of reps achieved", example = "1")
        Integer reps,

        @Schema(description = "Date the record was achieved", example = "2026-03-01")
        LocalDate achievedAt,

        @Schema(description = "UUID of the workout session exercise set where the record was achieved", example = "550e8400-e29b-41d4-a716-446655440003")
        UUID workoutSessionExerciseSetUuid,

        @Schema(description = "Timestamp when the record was created", example = "2026-03-01T10:00:00Z")
        Instant createdAt) {
}
