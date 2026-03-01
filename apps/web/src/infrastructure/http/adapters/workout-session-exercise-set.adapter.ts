import type { WorkoutSessionExerciseSetPort } from "@/src/domain/ports"
import type {
  WorkoutSessionExerciseSet,
  CreateWorkoutSessionExerciseSet,
} from "@/src/domain/models"
import { httpClient } from "../http-client"

export function createWorkoutSessionExerciseSetAdapter(): WorkoutSessionExerciseSetPort {
  return {
    create(
      workoutSessionExerciseUuid: string,
      data: CreateWorkoutSessionExerciseSet,
    ) {
      return httpClient.post<WorkoutSessionExerciseSet>(
        `/api/v1/workout-session-exercises/${workoutSessionExerciseUuid}/sets`,
        data,
      )
    },

    getByExercise(workoutSessionExerciseUuid: string) {
      return httpClient.get<WorkoutSessionExerciseSet[]>(
        `/api/v1/workout-session-exercises/${workoutSessionExerciseUuid}/sets`,
      )
    },

    getByUuid(workoutSessionExerciseUuid: string, uuid: string) {
      return httpClient.get<WorkoutSessionExerciseSet>(
        `/api/v1/workout-session-exercises/${workoutSessionExerciseUuid}/sets/${uuid}`,
      )
    },

    delete(workoutSessionExerciseUuid: string, uuid: string) {
      return httpClient.delete(
        `/api/v1/workout-session-exercises/${workoutSessionExerciseUuid}/sets/${uuid}`,
      )
    },
  }
}
