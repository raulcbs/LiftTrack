package com.lifttrack.api.application.usecases.workoutsessionexercise;

import com.lifttrack.api.domain.models.WorkoutSessionExercise;
import com.lifttrack.api.domain.ports.WorkoutSessionExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetWorkoutSessionExerciseByUuidUseCase {

    private final WorkoutSessionExerciseRepository workoutSessionExerciseRepository;

    public WorkoutSessionExercise execute(UUID uuid) {
        return workoutSessionExerciseRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("WorkoutSessionExercise not found: " + uuid));
    }
}
