package com.lifttrack.api.application.usecases.exercise;

import com.lifttrack.api.domain.ports.ExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteExerciseUseCase {

    private final ExerciseRepository exerciseRepository;

    public void execute(UUID uuid) {
        exerciseRepository.deleteByUuid(uuid);
    }
}
