import type { ResultAsync } from "neverthrow"
import type { PersonalRecord, CreatePersonalRecord } from "../models"
import type { DomainError } from "../errors"

export interface PersonalRecordPort {
  create(data: CreatePersonalRecord): ResultAsync<PersonalRecord, DomainError>
  getAll(): ResultAsync<PersonalRecord[], DomainError>
  getByExercise(
    exerciseUuid: string,
  ): ResultAsync<PersonalRecord[], DomainError>
  getByExerciseAndType(
    exerciseUuid: string,
    recordType: string,
  ): ResultAsync<PersonalRecord, DomainError>
  getByUuid(uuid: string): ResultAsync<PersonalRecord, DomainError>
  delete(uuid: string): ResultAsync<void, DomainError>
}
