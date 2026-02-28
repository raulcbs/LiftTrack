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
}
