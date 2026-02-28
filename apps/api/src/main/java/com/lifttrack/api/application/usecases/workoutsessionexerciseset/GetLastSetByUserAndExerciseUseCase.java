package com.lifttrack.api.application.usecases.workoutsessionexerciseset;

import com.lifttrack.api.domain.models.WorkoutSessionExerciseSet;
import com.lifttrack.api.domain.ports.WorkoutSessionExerciseSetRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetLastSetByUserAndExerciseUseCase {

    private final WorkoutSessionExerciseSetRepository workoutSessionExerciseSetRepository;

    public Optional<WorkoutSessionExerciseSet> execute(UUID userUuid, UUID exerciseUuid) {
        return workoutSessionExerciseSetRepository.findLastByUserUuidAndExerciseUuid(userUuid, exerciseUuid);
    }
}
