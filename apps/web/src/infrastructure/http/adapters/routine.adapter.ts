import type { RoutinePort } from "@/src/domain/ports"
import type { Routine, CreateRoutine } from "@/src/domain/models"
import { httpClient } from "../http-client"

export function createRoutineAdapter(): RoutinePort {
  return {
    create(data: CreateRoutine) {
      return httpClient.post<Routine>("/api/v1/routines", data)
    },

    getAll() {
      return httpClient.get<Routine[]>("/api/v1/routines")
    },

    getActive() {
      return httpClient.get<Routine[]>("/api/v1/routines/active")
    },

    getByUuid(uuid: string) {
      return httpClient.get<Routine>(`/api/v1/routines/${uuid}`)
    },

    delete(uuid: string) {
      return httpClient.delete(`/api/v1/routines/${uuid}`)
    },
  }
}
