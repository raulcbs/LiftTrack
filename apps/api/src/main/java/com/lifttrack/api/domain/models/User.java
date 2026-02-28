package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.util.UUID;

public record User(
        UUID uuid,
        String email,
        String password,
        String name,
        Instant createdAt,
        Instant updatedAt) {

    public User create(String email, String password, String name) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        return new User(null, email, password, name, null, null);
    }
}
