package com.lifttrack.api.application.usecases.user;

import com.lifttrack.api.domain.ports.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public void execute(UUID uuid) {
        userRepository.deleteByUuid(uuid);
    }
}
