package com.lifttrack.api.application.usecases.workoutsessionexerciseset;

import com.lifttrack.api.domain.ports.WorkoutSessionExerciseSetRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteWorkoutSessionExerciseSetUseCase {

    private final WorkoutSessionExerciseSetRepository workoutSessionExerciseSetRepository;

    public void execute(UUID uuid) {
        workoutSessionExerciseSetRepository.deleteByUuid(uuid);
    }
}
