package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.util.UUID;

public record MuscleGroup(
        UUID uuid,
        String name,
        Instant createdAt
) {
}
