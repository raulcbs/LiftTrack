package com.lifttrack.api.application.usecases.exercise;

import com.lifttrack.api.domain.models.Exercise;
import com.lifttrack.api.domain.ports.ExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetExercisesByUserUseCase {

    private final ExerciseRepository exerciseRepository;

    public List<Exercise> execute(UUID userUuid) {
        return exerciseRepository.findByUserUuid(userUuid);
    }
}
