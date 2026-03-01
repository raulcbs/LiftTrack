import type { PersonalRecordPort } from "@/src/domain/ports"
import type { PersonalRecord, CreatePersonalRecord } from "@/src/domain/models"
import { httpClient } from "../http-client"

export function createPersonalRecordAdapter(): PersonalRecordPort {
  return {
    create(data: CreatePersonalRecord) {
      return httpClient.post<PersonalRecord>("/api/v1/personal-records", data)
    },

    getAll() {
      return httpClient.get<PersonalRecord[]>("/api/v1/personal-records")
    },

    getByExercise(exerciseUuid: string) {
      return httpClient.get<PersonalRecord[]>(
        `/api/v1/personal-records?exerciseUuid=${exerciseUuid}`,
      )
    },

    getByExerciseAndType(exerciseUuid: string, recordType: string) {
      const params = new URLSearchParams({ exerciseUuid, recordType })
      return httpClient.get<PersonalRecord>(
        `/api/v1/personal-records?${params.toString()}`,
      )
    },

    getByUuid(uuid: string) {
      return httpClient.get<PersonalRecord>(`/api/v1/personal-records/${uuid}`)
    },

    delete(uuid: string) {
      return httpClient.delete(`/api/v1/personal-records/${uuid}`)
    },
  }
}
