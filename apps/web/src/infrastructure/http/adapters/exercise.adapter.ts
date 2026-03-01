import type { ExercisePort } from "@/src/domain/ports"
import type {
  Exercise,
  CreateExercise,
  WorkoutSessionExerciseSet,
} from "@/src/domain/models"
import { httpClient } from "../http-client"

export function createExerciseAdapter(): ExercisePort {
  return {
    create(data: CreateExercise) {
      return httpClient.post<Exercise>("/api/v1/exercises", data)
    },

    getByUuid(uuid: string) {
      return httpClient.get<Exercise>(`/api/v1/exercises/${uuid}`)
    },

    getGlobal() {
      return httpClient.get<Exercise[]>("/api/v1/exercises/global")
    },

    getByMuscleGroup(muscleGroupUuid: string) {
      return httpClient.get<Exercise[]>(
        `/api/v1/exercises?muscleGroupUuid=${muscleGroupUuid}`,
      )
    },

    getLastSet(exerciseUuid: string) {
      return httpClient.getNullable<WorkoutSessionExerciseSet>(
        `/api/v1/exercises/${exerciseUuid}/last-set`,
      )
    },

    delete(uuid: string) {
      return httpClient.delete(`/api/v1/exercises/${uuid}`)
    },
  }
}
