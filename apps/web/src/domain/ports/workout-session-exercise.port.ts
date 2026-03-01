import type { ResultAsync } from "neverthrow"
import type {
  WorkoutSessionExercise,
  CreateWorkoutSessionExercise,
} from "../models"
import type { DomainError } from "../errors"

export interface WorkoutSessionExercisePort {
  create(
    workoutSessionUuid: string,
    data: CreateWorkoutSessionExercise,
  ): ResultAsync<WorkoutSessionExercise, DomainError>
  getBySession(
    workoutSessionUuid: string,
  ): ResultAsync<WorkoutSessionExercise[], DomainError>
  getByUuid(
    workoutSessionUuid: string,
    uuid: string,
  ): ResultAsync<WorkoutSessionExercise, DomainError>
  delete(
    workoutSessionUuid: string,
    uuid: string,
  ): ResultAsync<void, DomainError>
}
