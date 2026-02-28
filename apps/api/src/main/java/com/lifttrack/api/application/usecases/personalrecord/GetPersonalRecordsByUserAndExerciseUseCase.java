package com.lifttrack.api.application.usecases.personalrecord;

import com.lifttrack.api.domain.models.PersonalRecord;
import com.lifttrack.api.domain.ports.PersonalRecordRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetPersonalRecordsByUserAndExerciseUseCase {

    private final PersonalRecordRepository personalRecordRepository;

    public List<PersonalRecord> execute(UUID userUuid, UUID exerciseUuid) {
        return personalRecordRepository.findByUserUuidAndExerciseUuid(userUuid, exerciseUuid);
    }
}
