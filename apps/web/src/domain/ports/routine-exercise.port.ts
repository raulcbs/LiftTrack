import type { ResultAsync } from "neverthrow"
import type { RoutineExercise, CreateRoutineExercise } from "../models"
import type { DomainError } from "../errors"

export interface RoutineExercisePort {
  create(
    routineDayUuid: string,
    data: CreateRoutineExercise,
  ): ResultAsync<RoutineExercise, DomainError>
  getByRoutineDay(
    routineDayUuid: string,
  ): ResultAsync<RoutineExercise[], DomainError>
  getByUuid(
    routineDayUuid: string,
    uuid: string,
  ): ResultAsync<RoutineExercise, DomainError>
  delete(routineDayUuid: string, uuid: string): ResultAsync<void, DomainError>
}
