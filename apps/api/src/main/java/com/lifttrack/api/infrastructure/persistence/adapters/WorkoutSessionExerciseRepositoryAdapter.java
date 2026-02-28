package com.lifttrack.api.infrastructure.persistence.adapters;

import com.lifttrack.api.domain.models.WorkoutSessionExercise;
import com.lifttrack.api.domain.ports.WorkoutSessionExerciseRepository;
import com.lifttrack.api.infrastructure.persistence.entities.ExerciseEntity;
import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionEntity;
import com.lifttrack.api.infrastructure.persistence.mappers.WorkoutSessionExerciseMapper;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaExerciseRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaWorkoutSessionExerciseRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaWorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WorkoutSessionExerciseRepositoryAdapter implements WorkoutSessionExerciseRepository {

    private final JpaWorkoutSessionExerciseRepository jpaWorkoutSessionExerciseRepository;
    private final JpaWorkoutSessionRepository jpaWorkoutSessionRepository;
    private final JpaExerciseRepository jpaExerciseRepository;

    @Override
    @Transactional
    public WorkoutSessionExercise save(WorkoutSessionExercise workoutSessionExercise) {
        WorkoutSessionEntity workoutSession = jpaWorkoutSessionRepository
                .findByUuid(workoutSessionExercise.workoutSessionUuid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "WorkoutSession not found: " + workoutSessionExercise.workoutSessionUuid()));

        ExerciseEntity exercise = jpaExerciseRepository
                .findByUuid(workoutSessionExercise.exerciseUuid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Exercise not found: " + workoutSessionExercise.exerciseUuid()));

        var entity = WorkoutSessionExerciseMapper.toEntity(workoutSessionExercise, workoutSession, exercise);
        return WorkoutSessionExerciseMapper.toDomain(jpaWorkoutSessionExerciseRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkoutSessionExercise> findByUuid(UUID uuid) {
        return jpaWorkoutSessionExerciseRepository.findByUuid(uuid)
                .map(WorkoutSessionExerciseMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutSessionExercise> findByWorkoutSessionUuid(UUID workoutSessionUuid) {
        return jpaWorkoutSessionExerciseRepository
                .findByWorkoutSessionUuidOrderBySortOrder(workoutSessionUuid)
                .stream()
                .map(WorkoutSessionExerciseMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        jpaWorkoutSessionExerciseRepository.deleteByUuid(uuid);
    }
}
