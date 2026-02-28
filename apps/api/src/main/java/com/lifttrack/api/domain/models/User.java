package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.util.UUID;

public record User(
        UUID uuid,
        String email,
        String password,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}
