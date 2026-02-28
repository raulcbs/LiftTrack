package com.lifttrack.api.application.usecases.routineexercise;

import com.lifttrack.api.domain.ports.RoutineExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteRoutineExerciseUseCase {

    private final RoutineExerciseRepository routineExerciseRepository;

    public void execute(UUID uuid) {
        routineExerciseRepository.deleteByUuid(uuid);
    }
}
