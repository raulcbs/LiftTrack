package com.lifttrack.api.infrastructure.persistence.mappers;

import com.lifttrack.api.domain.models.User;
import com.lifttrack.api.infrastructure.persistence.entities.UserEntity;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getUuid(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public static UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        entity.setUuid(domain.uuid());
        entity.setEmail(domain.email());
        entity.setPassword(domain.password());
        entity.setName(domain.name());
        entity.setCreatedAt(domain.createdAt());
        entity.setUpdatedAt(domain.updatedAt());
        return entity;
    }
}
