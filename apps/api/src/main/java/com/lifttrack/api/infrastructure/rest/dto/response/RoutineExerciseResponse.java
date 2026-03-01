package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Routine exercise details")
public record RoutineExerciseResponse(
        @Schema(description = "Unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID uuid,

        @Schema(description = "UUID of the parent routine day", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID routineDayUuid,

        @Schema(description = "UUID of the exercise", example = "550e8400-e29b-41d4-a716-446655440002")
        UUID exerciseUuid,

        @Schema(description = "Target number of sets", example = "4")
        Integer targetSets,

        @Schema(description = "Target rep range", example = "8-12")
        String targetRepRange,

        @Schema(description = "Sort order within the routine day", example = "0")
        int sortOrder,

        @Schema(description = "Notes for this exercise", example = "Focus on slow eccentric")
        String notes,

        @Schema(description = "Timestamp when the routine exercise was created", example = "2026-03-01T10:00:00Z")
        Instant createdAt) {
}
