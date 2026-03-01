package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Exercise details")
public record ExerciseResponse(
        @Schema(description = "Unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID uuid,

        @Schema(description = "Name of the exercise", example = "Bench Press")
        String name,

        @Schema(description = "UUID of the muscle group this exercise targets", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID muscleGroupUuid,

        @Schema(description = "UUID of the user who created this exercise (null for global exercises)", example = "550e8400-e29b-41d4-a716-446655440002")
        UUID userUuid,

        @Schema(description = "Timestamp when the exercise was created", example = "2026-03-01T10:00:00Z")
        Instant createdAt) {
}
