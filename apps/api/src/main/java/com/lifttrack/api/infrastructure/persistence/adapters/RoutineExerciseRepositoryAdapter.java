package com.lifttrack.api.infrastructure.persistence.adapters;

import com.lifttrack.api.domain.models.RoutineExercise;
import com.lifttrack.api.domain.ports.RoutineExerciseRepository;
import com.lifttrack.api.infrastructure.persistence.entities.ExerciseEntity;
import com.lifttrack.api.infrastructure.persistence.entities.RoutineDayEntity;
import com.lifttrack.api.infrastructure.persistence.mappers.RoutineExerciseMapper;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaExerciseRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaRoutineDayRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaRoutineExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoutineExerciseRepositoryAdapter implements RoutineExerciseRepository {

    private final JpaRoutineExerciseRepository jpaRoutineExerciseRepository;
    private final JpaRoutineDayRepository jpaRoutineDayRepository;
    private final JpaExerciseRepository jpaExerciseRepository;

    @Override
    @Transactional
    public RoutineExercise save(RoutineExercise routineExercise) {
        RoutineDayEntity routineDay = jpaRoutineDayRepository
                .findByUuid(routineExercise.routineDayUuid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "RoutineDay not found: " + routineExercise.routineDayUuid()));

        ExerciseEntity exercise = jpaExerciseRepository
                .findByUuid(routineExercise.exerciseUuid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Exercise not found: " + routineExercise.exerciseUuid()));

        var entity = RoutineExerciseMapper.toEntity(routineExercise, routineDay, exercise);
        return RoutineExerciseMapper.toDomain(jpaRoutineExerciseRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoutineExercise> findByUuid(UUID uuid) {
        return jpaRoutineExerciseRepository.findByUuid(uuid)
                .map(RoutineExerciseMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoutineExercise> findByRoutineDayUuid(UUID routineDayUuid) {
        return jpaRoutineExerciseRepository
                .findByRoutineDayUuidOrderBySortOrder(routineDayUuid)
                .stream()
                .map(RoutineExerciseMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        jpaRoutineExerciseRepository.deleteByUuid(uuid);
    }
}
