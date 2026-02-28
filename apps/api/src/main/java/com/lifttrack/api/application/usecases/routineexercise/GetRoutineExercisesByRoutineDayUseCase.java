package com.lifttrack.api.application.usecases.routineexercise;

import com.lifttrack.api.domain.models.RoutineExercise;
import com.lifttrack.api.domain.ports.RoutineExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetRoutineExercisesByRoutineDayUseCase {

    private final RoutineExerciseRepository routineExerciseRepository;

    public List<RoutineExercise> execute(UUID routineDayUuid) {
        return routineExerciseRepository.findByRoutineDayUuid(routineDayUuid);
    }
}
