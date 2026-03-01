import type { WorkoutSessionPort, DateRange } from "@/src/domain/ports"
import type { WorkoutSession, CreateWorkoutSession } from "@/src/domain/models"
import { httpClient } from "../http-client"

export function createWorkoutSessionAdapter(): WorkoutSessionPort {
  return {
    create(data: CreateWorkoutSession) {
      return httpClient.post<WorkoutSession>("/api/v1/workout-sessions", data)
    },

    getAll(dateRange?: DateRange) {
      let path = "/api/v1/workout-sessions"
      if (dateRange) {
        const params = new URLSearchParams({
          from: dateRange.from,
          to: dateRange.to,
        })
        path = `${path}?${params.toString()}`
      }
      return httpClient.get<WorkoutSession[]>(path)
    },

    getByUuid(uuid: string) {
      return httpClient.get<WorkoutSession>(`/api/v1/workout-sessions/${uuid}`)
    },

    delete(uuid: string) {
      return httpClient.delete(`/api/v1/workout-sessions/${uuid}`)
    },
  }
}
