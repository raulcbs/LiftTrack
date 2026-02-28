package com.lifttrack.api.application.usecases.workoutsessionexerciseset;

import com.lifttrack.api.domain.models.WorkoutSessionExerciseSet;
import com.lifttrack.api.domain.ports.WorkoutSessionExerciseSetRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetWorkoutSessionExerciseSetsByExerciseUseCase {

    private final WorkoutSessionExerciseSetRepository workoutSessionExerciseSetRepository;

    public List<WorkoutSessionExerciseSet> execute(UUID workoutSessionExerciseUuid) {
        return workoutSessionExerciseSetRepository.findByWorkoutSessionExerciseUuid(workoutSessionExerciseUuid);
    }
}
