package com.lifttrack.api.infrastructure.persistence.adapters;

import com.lifttrack.api.domain.models.Routine;
import com.lifttrack.api.domain.ports.RoutineRepository;
import com.lifttrack.api.infrastructure.persistence.entities.UserEntity;
import com.lifttrack.api.infrastructure.persistence.mappers.RoutineMapper;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaRoutineRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoutineRepositoryAdapter implements RoutineRepository {

    private final JpaRoutineRepository jpaRoutineRepository;
    private final JpaUserRepository jpaUserRepository;

    @Override
    @Transactional
    public Routine save(Routine routine) {
        UserEntity user = jpaUserRepository.findByUuid(routine.userUuid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found: " + routine.userUuid()));

        var entity = RoutineMapper.toEntity(routine, user);
        return RoutineMapper.toDomain(jpaRoutineRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Routine> findByUuid(UUID uuid) {
        return jpaRoutineRepository.findByUuid(uuid)
                .map(RoutineMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Routine> findByUserUuid(UUID userUuid) {
        return jpaRoutineRepository.findByUserUuid(userUuid)
                .stream()
                .map(RoutineMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Routine> findActiveByUserUuid(UUID userUuid) {
        return jpaRoutineRepository.findByUserUuidAndActiveTrue(userUuid)
                .stream()
                .map(RoutineMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        jpaRoutineRepository.deleteByUuid(uuid);
    }
}
