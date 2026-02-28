package com.lifttrack.api.domain.ports;

import com.lifttrack.api.domain.models.RoutineExercise;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutineExerciseRepository {

    RoutineExercise save(RoutineExercise routineExercise);

    Optional<RoutineExercise> findByUuid(UUID uuid);

    List<RoutineExercise> findByRoutineDayUuid(UUID routineDayUuid);

    void deleteByUuid(UUID uuid);
}
