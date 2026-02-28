package com.lifttrack.api.application.usecases.personalrecord;

import com.lifttrack.api.domain.models.PersonalRecord;
import com.lifttrack.api.domain.ports.PersonalRecordRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetPersonalRecordByUuidUseCase {

    private final PersonalRecordRepository personalRecordRepository;

    public PersonalRecord execute(UUID uuid) {
        return personalRecordRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("PersonalRecord not found: " + uuid));
    }
}
