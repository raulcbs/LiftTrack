package com.lifttrack.api.application.usecases.routine;

import com.lifttrack.api.domain.models.Routine;
import com.lifttrack.api.domain.ports.RoutineRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetRoutinesByUserUseCase {

    private final RoutineRepository routineRepository;

    public List<Routine> execute(UUID userUuid) {
        return routineRepository.findByUserUuid(userUuid);
    }
}
