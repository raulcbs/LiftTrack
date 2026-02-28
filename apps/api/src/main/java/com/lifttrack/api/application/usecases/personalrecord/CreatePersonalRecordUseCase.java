package com.lifttrack.api.application.usecases.personalrecord;

import com.lifttrack.api.domain.models.PersonalRecord;
import com.lifttrack.api.domain.ports.PersonalRecordRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePersonalRecordUseCase {

    private final PersonalRecordRepository personalRecordRepository;

    public PersonalRecord execute(PersonalRecord personalRecord) {
        return personalRecordRepository.save(personalRecord);
    }
}
