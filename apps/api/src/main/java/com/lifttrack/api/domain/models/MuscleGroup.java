package com.lifttrack.api.domain.models;

import java.time.Instant;
import java.util.UUID;

public record MuscleGroup(
		UUID uuid,
		String name,
		Instant createdAt) {

	public MuscleGroup create(String name) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Muscle group name cannot be null or blank");
		}

		return new MuscleGroup(null, name, null);
	}
}
