import type { MuscleGroupPort } from "@/src/domain/ports"
import type { MuscleGroup } from "@/src/domain/models"
import { httpClient } from "../http-client"

export function createMuscleGroupAdapter(): MuscleGroupPort {
  return {
    getAll() {
      return httpClient.get<MuscleGroup[]>("/api/v1/muscle-groups")
    },

    getByUuid(uuid: string) {
      return httpClient.get<MuscleGroup>(`/api/v1/muscle-groups/${uuid}`)
    },
  }
}
