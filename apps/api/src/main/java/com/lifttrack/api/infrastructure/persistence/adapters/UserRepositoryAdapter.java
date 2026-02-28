package com.lifttrack.api.infrastructure.persistence.adapters;

import com.lifttrack.api.domain.models.User;
import com.lifttrack.api.domain.ports.UserRepository;
import com.lifttrack.api.infrastructure.persistence.mappers.UserMapper;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    @Transactional
    public User save(User user) {
        var entity = UserMapper.toEntity(user);
        return UserMapper.toDomain(jpaUserRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUuid(UUID uuid) {
        return jpaUserRepository.findByUuid(uuid)
                .map(UserMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        jpaUserRepository.deleteByUuid(uuid);
    }
}
