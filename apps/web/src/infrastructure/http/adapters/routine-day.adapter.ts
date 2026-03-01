import type { RoutineDayPort } from "@/src/domain/ports"
import type { RoutineDay, CreateRoutineDay } from "@/src/domain/models"
import { httpClient } from "../http-client"

export function createRoutineDayAdapter(): RoutineDayPort {
  return {
    create(routineUuid: string, data: CreateRoutineDay) {
      return httpClient.post<RoutineDay>(
        `/api/v1/routines/${routineUuid}/days`,
        data,
      )
    },

    getByRoutine(routineUuid: string) {
      return httpClient.get<RoutineDay[]>(
        `/api/v1/routines/${routineUuid}/days`,
      )
    },

    getByUuid(routineUuid: string, uuid: string) {
      return httpClient.get<RoutineDay>(
        `/api/v1/routines/${routineUuid}/days/${uuid}`,
      )
    },

    delete(routineUuid: string, uuid: string) {
      return httpClient.delete(`/api/v1/routines/${routineUuid}/days/${uuid}`)
    },
  }
}
