package com.lifttrack.api.infrastructure.rest.dto.mapper;

import com.lifttrack.api.domain.models.MuscleGroup;
import com.lifttrack.api.infrastructure.rest.dto.response.MuscleGroupResponse;

public final class MuscleGroupResponseMapper {

    private MuscleGroupResponseMapper() {
    }

    public static MuscleGroupResponse toResponse(MuscleGroup muscleGroup) {
        return new MuscleGroupResponse(
                muscleGroup.uuid(),
                muscleGroup.name());
    }
}
