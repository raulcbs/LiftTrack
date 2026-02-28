package com.lifttrack.api.application.usecases.musclegroup;

import com.lifttrack.api.domain.models.MuscleGroup;
import com.lifttrack.api.domain.ports.MuscleGroupRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllMuscleGroupsUseCase {

    private final MuscleGroupRepository muscleGroupRepository;

    public List<MuscleGroup> execute() {
        return muscleGroupRepository.findAll();
    }
}
