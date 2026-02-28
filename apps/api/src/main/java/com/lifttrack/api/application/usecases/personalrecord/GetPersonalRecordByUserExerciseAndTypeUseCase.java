package com.lifttrack.api.application.usecases.personalrecord;

import com.lifttrack.api.domain.models.PersonalRecord;
import com.lifttrack.api.domain.ports.PersonalRecordRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetPersonalRecordByUserExerciseAndTypeUseCase {

    private final PersonalRecordRepository personalRecordRepository;

    public Optional<PersonalRecord> execute(UUID userUuid, UUID exerciseUuid, String recordType) {
        return personalRecordRepository.findByUserUuidAndExerciseUuidAndRecordType(userUuid, exerciseUuid, recordType);
    }
}
