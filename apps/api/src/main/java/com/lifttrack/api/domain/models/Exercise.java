package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.util.UUID;

public record Exercise(
        UUID uuid,
        String name,
        UUID muscleGroupUuid,
        UUID userUuid,
        Instant createdAt
) {

    public Exercise create(String name, UUID muscleGroupUuid, UUID userUuid) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Exercise name cannot be null or blank");
        }

        if (muscleGroupUuid == null) {
            throw new IllegalArgumentException("Muscle group UUID cannot be null");
        }

        return new Exercise(null, name, muscleGroupUuid, userUuid, null);
    }

}
