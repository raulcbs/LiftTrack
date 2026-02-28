package com.lifttrack.api.application.usecases.user;

import com.lifttrack.api.domain.models.User;
import com.lifttrack.api.domain.ports.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetUserByUuidUseCase {

    private final UserRepository userRepository;

    public User execute(UUID uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + uuid));
    }
}
