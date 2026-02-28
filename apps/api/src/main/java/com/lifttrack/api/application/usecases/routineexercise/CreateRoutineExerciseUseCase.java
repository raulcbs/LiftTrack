package com.lifttrack.api.application.usecases.routineexercise;

import com.lifttrack.api.domain.models.RoutineExercise;
import com.lifttrack.api.domain.ports.RoutineExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRoutineExerciseUseCase {

    private final RoutineExerciseRepository routineExerciseRepository;

    public RoutineExercise execute(RoutineExercise routineExercise) {
        return routineExerciseRepository.save(routineExercise);
    }
}
