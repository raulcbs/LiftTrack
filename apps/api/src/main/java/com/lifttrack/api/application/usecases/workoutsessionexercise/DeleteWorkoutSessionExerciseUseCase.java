package com.lifttrack.api.application.usecases.workoutsessionexercise;

import com.lifttrack.api.domain.ports.WorkoutSessionExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteWorkoutSessionExerciseUseCase {

    private final WorkoutSessionExerciseRepository workoutSessionExerciseRepository;

    public void execute(UUID uuid) {
        workoutSessionExerciseRepository.deleteByUuid(uuid);
    }
}
