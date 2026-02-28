package com.lifttrack.api.application.usecases.user;

import com.lifttrack.api.domain.models.User;
import com.lifttrack.api.domain.ports.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetUserByEmailUseCase {

    private final UserRepository userRepository;

    public Optional<User> execute(String email) {
        return userRepository.findByEmail(email);
    }
}
