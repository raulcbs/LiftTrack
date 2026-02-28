package com.lifttrack.api.infrastructure.persistence.adapters;

import com.lifttrack.api.domain.models.WorkoutSession;
import com.lifttrack.api.domain.ports.WorkoutSessionRepository;
import com.lifttrack.api.infrastructure.persistence.entities.RoutineDayEntity;
import com.lifttrack.api.infrastructure.persistence.entities.UserEntity;
import com.lifttrack.api.infrastructure.persistence.mappers.WorkoutSessionMapper;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaRoutineDayRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaUserRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaWorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WorkoutSessionRepositoryAdapter implements WorkoutSessionRepository {

    private final JpaWorkoutSessionRepository jpaWorkoutSessionRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaRoutineDayRepository jpaRoutineDayRepository;

    @Override
    @Transactional
    public WorkoutSession save(WorkoutSession workoutSession) {
        UserEntity user = jpaUserRepository.findByUuid(workoutSession.userUuid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found: " + workoutSession.userUuid()));

        RoutineDayEntity routineDay = null;
        if (workoutSession.routineDayUuid() != null) {
            routineDay = jpaRoutineDayRepository.findByUuid(workoutSession.routineDayUuid())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "RoutineDay not found: " + workoutSession.routineDayUuid()));
        }

        var entity = WorkoutSessionMapper.toEntity(workoutSession, user, routineDay);
        return WorkoutSessionMapper.toDomain(jpaWorkoutSessionRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkoutSession> findByUuid(UUID uuid) {
        return jpaWorkoutSessionRepository.findByUuid(uuid)
                .map(WorkoutSessionMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutSession> findByUserUuid(UUID userUuid) {
        return jpaWorkoutSessionRepository.findByUserUuidOrderBySessionDateDesc(userUuid)
                .stream()
                .map(WorkoutSessionMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutSession> findByUserUuidAndDateRange(UUID userUuid, LocalDate from, LocalDate to) {
        return jpaWorkoutSessionRepository
                .findByUserUuidAndSessionDateBetweenOrderBySessionDateDesc(userUuid, from, to)
                .stream()
                .map(WorkoutSessionMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        jpaWorkoutSessionRepository.deleteByUuid(uuid);
    }
}
