package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.util.UUID;

public record Routine(
        UUID uuid,
        UUID userUuid,
        String name,
        String description,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
}
