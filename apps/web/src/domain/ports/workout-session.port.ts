import type { ResultAsync } from "neverthrow"
import type { WorkoutSession, CreateWorkoutSession } from "../models"
import type { DomainError } from "../errors"

export interface DateRange {
  readonly from: string
  readonly to: string
}

export interface WorkoutSessionPort {
  create(data: CreateWorkoutSession): ResultAsync<WorkoutSession, DomainError>
  getAll(dateRange?: DateRange): ResultAsync<WorkoutSession[], DomainError>
  getByUuid(uuid: string): ResultAsync<WorkoutSession, DomainError>
  delete(uuid: string): ResultAsync<void, DomainError>
}
