import type { ResultAsync } from "neverthrow"
import type { Routine, CreateRoutine } from "../models"
import type { DomainError } from "../errors"

export interface RoutinePort {
  create(data: CreateRoutine): ResultAsync<Routine, DomainError>
  getAll(): ResultAsync<Routine[], DomainError>
  getActive(): ResultAsync<Routine[], DomainError>
  getByUuid(uuid: string): ResultAsync<Routine, DomainError>
  delete(uuid: string): ResultAsync<void, DomainError>
}
