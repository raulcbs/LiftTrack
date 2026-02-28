package com.lifttrack.api.domain.ports;

import com.lifttrack.api.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUuid(UUID uuid);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByUuid(UUID uuid);
}
