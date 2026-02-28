package com.lifttrack.api.infrastructure.persistence.mappers;

import com.lifttrack.api.domain.models.MuscleGroup;
import com.lifttrack.api.infrastructure.persistence.entities.MuscleGroupEntity;

public final class MuscleGroupMapper {

    private MuscleGroupMapper() {
    }

    public static MuscleGroup toDomain(MuscleGroupEntity entity) {
        return new MuscleGroup(
                entity.getUuid(),
                entity.getName(),
                entity.getCreatedAt()
        );
    }

    public static MuscleGroupEntity toEntity(MuscleGroup domain) {
        MuscleGroupEntity entity = new MuscleGroupEntity();
        entity.setUuid(domain.uuid());
        entity.setName(domain.name());
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }
}
