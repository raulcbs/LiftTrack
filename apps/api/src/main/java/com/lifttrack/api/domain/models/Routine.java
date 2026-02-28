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
        Instant updatedAt) {

    public Routine create(UUID userUuid, String name, String description) {
        if (userUuid == null) {
            throw new IllegalArgumentException("User UUID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Routine name cannot be null or blank");
        }

        return new Routine(null, userUuid, name, description, true, null, null);
    }
}
