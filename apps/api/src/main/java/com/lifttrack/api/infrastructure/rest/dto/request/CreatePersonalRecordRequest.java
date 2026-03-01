package com.lifttrack.api.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Request body for creating a new personal record")
public record CreatePersonalRecordRequest(
        @Schema(description = "UUID of the exercise", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull(message = "Exercise UUID is required")
        UUID exerciseUuid,

        @Schema(description = "Type of personal record", example = "ONE_REP_MAX")
        @NotBlank(message = "Record type cannot be blank")
        String recordType,

        @Schema(description = "Weight achieved in kilograms", example = "120.0", minimum = "0.0")
        @DecimalMin(value = "0.0", message = "Weight must be zero or positive")
        BigDecimal weight,

        @Schema(description = "Number of reps achieved (optional)", example = "1", minimum = "0")
        @Min(value = 0, message = "Reps must be zero or positive")
        Integer reps,

        @Schema(description = "Date the record was achieved", example = "2026-03-01")
        @NotNull(message = "Achieved date is required")
        LocalDate achievedAt,

        @Schema(description = "UUID of the workout session exercise set where the record was achieved (optional)", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID workoutSessionExerciseSetUuid) {
}
