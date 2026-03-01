package com.lifttrack.api.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Request body for creating a set within a workout session exercise")
public record CreateWorkoutSessionExerciseSetRequest(
        @Schema(description = "Set number within the exercise", example = "1", minimum = "1")
        @Min(value = 1, message = "Set number must be at least 1")
        int setNumber,

        @Schema(description = "Number of reps performed", example = "10", minimum = "0")
        @Min(value = 0, message = "Reps must be zero or positive")
        int reps,

        @Schema(description = "Weight used in kilograms", example = "80.0", minimum = "0.0")
        @NotNull(message = "Weight is required")
        @DecimalMin(value = "0.0", message = "Weight must be zero or positive")
        BigDecimal weight,

        @Schema(description = "Reps in reserve (optional)", example = "2", minimum = "0")
        @Min(value = 0, message = "RIR must be zero or positive")
        Integer rir,

        @Schema(description = "Whether this is a warmup set", example = "false")
        boolean warmup) {
}
