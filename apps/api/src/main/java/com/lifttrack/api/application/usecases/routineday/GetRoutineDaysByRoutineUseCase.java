package com.lifttrack.api.application.usecases.routineday;

import com.lifttrack.api.domain.models.RoutineDay;
import com.lifttrack.api.domain.ports.RoutineDayRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetRoutineDaysByRoutineUseCase {

    private final RoutineDayRepository routineDayRepository;

    public List<RoutineDay> execute(UUID routineUuid) {
        return routineDayRepository.findByRoutineUuid(routineUuid);
    }
}
