package com.lifttrack.api.application.usecases.routineday;

import com.lifttrack.api.domain.models.RoutineDay;
import com.lifttrack.api.domain.ports.RoutineDayRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRoutineDayUseCase {

    private final RoutineDayRepository routineDayRepository;

    public RoutineDay execute(RoutineDay routineDay) {
        return routineDayRepository.save(routineDay);
    }
}
