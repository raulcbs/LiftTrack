package com.lifttrack.api.application.usecases.routine;

import com.lifttrack.api.domain.models.Routine;
import com.lifttrack.api.domain.ports.RoutineRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRoutineUseCase {

    private final RoutineRepository routineRepository;

    public Routine execute(Routine routine) {
        return routineRepository.save(routine);
    }
}
