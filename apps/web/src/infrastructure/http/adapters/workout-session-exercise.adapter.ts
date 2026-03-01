import type { WorkoutSessionExercisePort } from "@/src/domain/ports"
import type {
  WorkoutSessionExercise,
  CreateWorkoutSessionExercise,
} from "@/src/domain/models"
import { httpClient } from "../http-client"

export function createWorkoutSessionExerciseAdapter(): WorkoutSessionExercisePort {
  return {
    create(workoutSessionUuid: string, data: CreateWorkoutSessionExercise) {
      return httpClient.post<WorkoutSessionExercise>(
        `/api/v1/workout-sessions/${workoutSessionUuid}/exercises`,
        data,
      )
    },

    getBySession(workoutSessionUuid: string) {
      return httpClient.get<WorkoutSessionExercise[]>(
        `/api/v1/workout-sessions/${workoutSessionUuid}/exercises`,
      )
    },

    getByUuid(workoutSessionUuid: string, uuid: string) {
      return httpClient.get<WorkoutSessionExercise>(
        `/api/v1/workout-sessions/${workoutSessionUuid}/exercises/${uuid}`,
      )
    },

    delete(workoutSessionUuid: string, uuid: string) {
      return httpClient.delete(
        `/api/v1/workout-sessions/${workoutSessionUuid}/exercises/${uuid}`,
      )
    },
  }
}
