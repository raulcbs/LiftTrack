package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Muscle group details")
public record MuscleGroupResponse(
        @Schema(description = "Unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID uuid,

        @Schema(description = "Name of the muscle group", example = "Chest")
        String name) {
}
