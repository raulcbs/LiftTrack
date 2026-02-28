package com.lifttrack.api.application.usecases.workoutsessionexercise;

import com.lifttrack.api.domain.models.WorkoutSessionExercise;
import com.lifttrack.api.domain.ports.WorkoutSessionExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkoutSessionExerciseUseCase {

    private final WorkoutSessionExerciseRepository workoutSessionExerciseRepository;

    public WorkoutSessionExercise execute(WorkoutSessionExercise workoutSessionExercise) {
        return workoutSessionExerciseRepository.save(workoutSessionExercise);
    }
}
