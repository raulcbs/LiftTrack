package com.lifttrack.api.application.usecases.user;

import com.lifttrack.api.domain.models.User;
import com.lifttrack.api.domain.ports.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public User execute(User user) {
        if (userRepository.existsByEmail(user.email())) {
            throw new IllegalArgumentException("Email already exists: " + user.email());
        }
        return userRepository.save(user);
    }
}
