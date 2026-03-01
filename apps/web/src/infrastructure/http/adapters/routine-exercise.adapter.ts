import type { RoutineExercisePort } from "@/src/domain/ports"
import type {
  RoutineExercise,
  CreateRoutineExercise,
} from "@/src/domain/models"
import { httpClient } from "../http-client"

export function createRoutineExerciseAdapter(): RoutineExercisePort {
  return {
    create(routineDayUuid: string, data: CreateRoutineExercise) {
      return httpClient.post<RoutineExercise>(
        `/api/v1/routine-days/${routineDayUuid}/exercises`,
        data,
      )
    },

    getByRoutineDay(routineDayUuid: string) {
      return httpClient.get<RoutineExercise[]>(
        `/api/v1/routine-days/${routineDayUuid}/exercises`,
      )
    },

    getByUuid(routineDayUuid: string, uuid: string) {
      return httpClient.get<RoutineExercise>(
        `/api/v1/routine-days/${routineDayUuid}/exercises/${uuid}`,
      )
    },

    delete(routineDayUuid: string, uuid: string) {
      return httpClient.delete(
        `/api/v1/routine-days/${routineDayUuid}/exercises/${uuid}`,
      )
    },
  }
}
