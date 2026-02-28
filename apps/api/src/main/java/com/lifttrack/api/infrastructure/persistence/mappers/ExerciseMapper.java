package com.lifttrack.api.infrastructure.persistence.mappers;

import com.lifttrack.api.domain.models.Exercise;
import com.lifttrack.api.infrastructure.persistence.entities.ExerciseEntity;
import com.lifttrack.api.infrastructure.persistence.entities.MuscleGroupEntity;
import com.lifttrack.api.infrastructure.persistence.entities.UserEntity;

public final class ExerciseMapper {

    private ExerciseMapper() {
    }

    public static Exercise toDomain(ExerciseEntity entity) {
        return new Exercise(
                entity.getUuid(),
                entity.getName(),
                entity.getMuscleGroup().getUuid(),
                entity.getUser() != null ? entity.getUser().getUuid() : null,
                entity.getCreatedAt());
    }

    public static ExerciseEntity toEntity(Exercise domain, MuscleGroupEntity muscleGroup, UserEntity user) {
        ExerciseEntity entity = new ExerciseEntity();
        entity.setUuid(domain.uuid());
        entity.setName(domain.name());
        entity.setMuscleGroup(muscleGroup);
        entity.setUser(user);
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }
}
