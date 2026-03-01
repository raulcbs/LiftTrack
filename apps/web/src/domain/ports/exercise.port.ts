import type { ResultAsync } from "neverthrow"
import type {
  Exercise,
  CreateExercise,
  WorkoutSessionExerciseSet,
} from "../models"
import type { DomainError } from "../errors"

export interface ExercisePort {
  create(data: CreateExercise): ResultAsync<Exercise, DomainError>
  getByUuid(uuid: string): ResultAsync<Exercise, DomainError>
  getGlobal(): ResultAsync<Exercise[], DomainError>
  getByMuscleGroup(
    muscleGroupUuid: string,
  ): ResultAsync<Exercise[], DomainError>
  getLastSet(
    exerciseUuid: string,
  ): ResultAsync<WorkoutSessionExerciseSet | null, DomainError>
  delete(uuid: string): ResultAsync<void, DomainError>
}
