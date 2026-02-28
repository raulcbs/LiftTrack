package com.lifttrack.api.infrastructure.persistence.adapters;

import com.lifttrack.api.domain.models.Exercise;
import com.lifttrack.api.domain.ports.ExerciseRepository;
import com.lifttrack.api.infrastructure.persistence.entities.MuscleGroupEntity;
import com.lifttrack.api.infrastructure.persistence.entities.UserEntity;
import com.lifttrack.api.infrastructure.persistence.mappers.ExerciseMapper;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaExerciseRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaMuscleGroupRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExerciseRepositoryAdapter implements ExerciseRepository {

    private final JpaExerciseRepository jpaExerciseRepository;
    private final JpaMuscleGroupRepository jpaMuscleGroupRepository;
    private final JpaUserRepository jpaUserRepository;

    @Override
    @Transactional
    public Exercise save(Exercise exercise) {
        MuscleGroupEntity muscleGroup = jpaMuscleGroupRepository
                .findByUuid(exercise.muscleGroupUuid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "MuscleGroup not found: " + exercise.muscleGroupUuid()));

        UserEntity user = null;
        if (exercise.userUuid() != null) {
            user = jpaUserRepository.findByUuid(exercise.userUuid())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "User not found: " + exercise.userUuid()));
        }

        var entity = ExerciseMapper.toEntity(exercise, muscleGroup, user);
        return ExerciseMapper.toDomain(jpaExerciseRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Exercise> findByUuid(UUID uuid) {
        return jpaExerciseRepository.findByUuid(uuid)
                .map(ExerciseMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Exercise> findByMuscleGroupUuid(UUID muscleGroupUuid) {
        return jpaExerciseRepository.findByMuscleGroupUuid(muscleGroupUuid)
                .stream()
                .map(ExerciseMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Exercise> findGlobalExercises() {
        return jpaExerciseRepository.findByUserIsNull()
                .stream()
                .map(ExerciseMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Exercise> findByUserUuid(UUID userUuid) {
        return jpaExerciseRepository.findByUserUuid(userUuid)
                .stream()
                .map(ExerciseMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        jpaExerciseRepository.deleteByUuid(uuid);
    }
}
