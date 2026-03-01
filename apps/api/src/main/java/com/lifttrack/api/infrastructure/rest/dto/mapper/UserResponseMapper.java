package com.lifttrack.api.infrastructure.rest.dto.mapper;

import com.lifttrack.api.domain.models.User;
import com.lifttrack.api.infrastructure.rest.dto.response.UserResponse;

public final class UserResponseMapper {

    private UserResponseMapper() {
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.uuid(),
                user.email(),
                user.name(),
                user.createdAt());
    }
}
