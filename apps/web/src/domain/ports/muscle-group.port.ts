import type { ResultAsync } from "neverthrow"
import type { MuscleGroup } from "../models"
import type { DomainError } from "../errors"

export interface MuscleGroupPort {
  getAll(): ResultAsync<MuscleGroup[], DomainError>
  getByUuid(uuid: string): ResultAsync<MuscleGroup, DomainError>
}
