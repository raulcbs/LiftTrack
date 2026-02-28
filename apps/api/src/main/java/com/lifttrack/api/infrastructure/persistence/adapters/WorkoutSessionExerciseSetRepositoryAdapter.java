package com.lifttrack.api.infrastructure.persistence.adapters;

import com.lifttrack.api.domain.models.WorkoutSessionExerciseSet;
import com.lifttrack.api.domain.ports.WorkoutSessionExerciseSetRepository;
import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionExerciseEntity;
import com.lifttrack.api.infrastructure.persistence.mappers.WorkoutSessionExerciseSetMapper;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaWorkoutSessionExerciseRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaWorkoutSessionExerciseSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WorkoutSessionExerciseSetRepositoryAdapter implements WorkoutSessionExerciseSetRepository {

    private final JpaWorkoutSessionExerciseSetRepository jpaWorkoutSessionExerciseSetRepository;
    private final JpaWorkoutSessionExerciseRepository jpaWorkoutSessionExerciseRepository;

    @Override
    @Transactional
    public WorkoutSessionExerciseSet save(WorkoutSessionExerciseSet exerciseSet) {
        WorkoutSessionExerciseEntity workoutSessionExercise = jpaWorkoutSessionExerciseRepository
                .findByUuid(exerciseSet.workoutSessionExerciseUuid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "WorkoutSessionExercise not found: " + exerciseSet.workoutSessionExerciseUuid()));

        var entity = WorkoutSessionExerciseSetMapper.toEntity(exerciseSet, workoutSessionExercise);
        return WorkoutSessionExerciseSetMapper.toDomain(jpaWorkoutSessionExerciseSetRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkoutSessionExerciseSet> findByUuid(UUID uuid) {
        return jpaWorkoutSessionExerciseSetRepository.findByUuid(uuid)
                .map(WorkoutSessionExerciseSetMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutSessionExerciseSet> findByWorkoutSessionExerciseUuid(UUID workoutSessionExerciseUuid) {
        return jpaWorkoutSessionExerciseSetRepository
                .findByWorkoutSessionExerciseUuidOrderBySetNumber(workoutSessionExerciseUuid)
                .stream()
                .map(WorkoutSessionExerciseSetMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkoutSessionExerciseSet> findLastByUserUuidAndExerciseUuid(UUID userUuid, UUID exerciseUuid) {
        return jpaWorkoutSessionExerciseSetRepository
                .findLastByUserUuidAndExerciseUuid(userUuid, exerciseUuid)
                .map(WorkoutSessionExerciseSetMapper::toDomain);
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        jpaWorkoutSessionExerciseSetRepository.deleteByUuid(uuid);
    }
}
