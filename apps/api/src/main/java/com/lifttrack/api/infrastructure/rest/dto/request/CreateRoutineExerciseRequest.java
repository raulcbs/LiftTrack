package com.lifttrack.api.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Request body for adding an exercise to a routine day")
public record CreateRoutineExerciseRequest(
        @Schema(description = "UUID of the exercise to add", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull(message = "Exercise UUID is required")
        UUID exerciseUuid,

        @Schema(description = "Target number of sets", example = "4", minimum = "1")
        @Min(value = 1, message = "Target sets must be at least 1")
        Integer targetSets,

        @Schema(description = "Target rep range", example = "8-12")
        String targetRepRange,

        @Schema(description = "Sort order within the routine day", example = "0", minimum = "0")
        @Min(value = 0, message = "Sort order must be zero or positive")
        int sortOrder,

        @Schema(description = "Optional notes for this exercise", example = "Focus on slow eccentric")
        String notes) {
}
