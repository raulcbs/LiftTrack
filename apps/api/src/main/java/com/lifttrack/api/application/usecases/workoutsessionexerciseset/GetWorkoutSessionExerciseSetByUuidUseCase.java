package com.lifttrack.api.application.usecases.workoutsessionexerciseset;

import com.lifttrack.api.domain.models.WorkoutSessionExerciseSet;
import com.lifttrack.api.domain.ports.WorkoutSessionExerciseSetRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetWorkoutSessionExerciseSetByUuidUseCase {

    private final WorkoutSessionExerciseSetRepository workoutSessionExerciseSetRepository;

    public WorkoutSessionExerciseSet execute(UUID uuid) {
        return workoutSessionExerciseSetRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("WorkoutSessionExerciseSet not found: " + uuid));
    }
}
