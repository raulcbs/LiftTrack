import type { ResultAsync } from "neverthrow"
import type { RoutineDay, CreateRoutineDay } from "../models"
import type { DomainError } from "../errors"

export interface RoutineDayPort {
  create(
    routineUuid: string,
    data: CreateRoutineDay,
  ): ResultAsync<RoutineDay, DomainError>
  getByRoutine(routineUuid: string): ResultAsync<RoutineDay[], DomainError>
  getByUuid(
    routineUuid: string,
    uuid: string,
  ): ResultAsync<RoutineDay, DomainError>
  delete(routineUuid: string, uuid: string): ResultAsync<void, DomainError>
}
