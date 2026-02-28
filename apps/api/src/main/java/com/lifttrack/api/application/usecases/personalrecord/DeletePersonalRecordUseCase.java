package com.lifttrack.api.application.usecases.personalrecord;

import com.lifttrack.api.domain.ports.PersonalRecordRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeletePersonalRecordUseCase {

    private final PersonalRecordRepository personalRecordRepository;

    public void execute(UUID uuid) {
        personalRecordRepository.deleteByUuid(uuid);
    }
}
