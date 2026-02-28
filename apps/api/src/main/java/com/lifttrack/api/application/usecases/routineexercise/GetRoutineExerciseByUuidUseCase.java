package com.lifttrack.api.application.usecases.routineexercise;

import com.lifttrack.api.domain.models.RoutineExercise;
import com.lifttrack.api.domain.ports.RoutineExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetRoutineExerciseByUuidUseCase {

    private final RoutineExerciseRepository routineExerciseRepository;

    public RoutineExercise execute(UUID uuid) {
        return routineExerciseRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("RoutineExercise not found: " + uuid));
    }
}
