import type { ResultAsync } from "neverthrow"
import type {
  WorkoutSessionExerciseSet,
  CreateWorkoutSessionExerciseSet,
} from "../models"
import type { DomainError } from "../errors"

export interface WorkoutSessionExerciseSetPort {
  create(
    workoutSessionExerciseUuid: string,
    data: CreateWorkoutSessionExerciseSet,
  ): ResultAsync<WorkoutSessionExerciseSet, DomainError>
  getByExercise(
    workoutSessionExerciseUuid: string,
  ): ResultAsync<WorkoutSessionExerciseSet[], DomainError>
  getByUuid(
    workoutSessionExerciseUuid: string,
    uuid: string,
  ): ResultAsync<WorkoutSessionExerciseSet, DomainError>
  delete(
    workoutSessionExerciseUuid: string,
    uuid: string,
  ): ResultAsync<void, DomainError>
}
