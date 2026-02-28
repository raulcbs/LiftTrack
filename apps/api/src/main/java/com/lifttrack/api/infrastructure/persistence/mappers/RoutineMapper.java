package com.lifttrack.api.infrastructure.persistence.mappers;

import com.lifttrack.api.domain.models.Routine;
import com.lifttrack.api.infrastructure.persistence.entities.RoutineEntity;
import com.lifttrack.api.infrastructure.persistence.entities.UserEntity;

public final class RoutineMapper {

    private RoutineMapper() {
    }

    public static Routine toDomain(RoutineEntity entity) {
        return new Routine(
                entity.getUuid(),
                entity.getUser().getUuid(),
                entity.getName(),
                entity.getDescription(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public static RoutineEntity toEntity(Routine domain, UserEntity user) {
        RoutineEntity entity = new RoutineEntity();
        entity.setUuid(domain.uuid());
        entity.setUser(user);
        entity.setName(domain.name());
        entity.setDescription(domain.description());
        entity.setActive(domain.active());
        entity.setCreatedAt(domain.createdAt());
        entity.setUpdatedAt(domain.updatedAt());
        return entity;
    }
}
