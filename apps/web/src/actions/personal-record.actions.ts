"use server"

import { createPersonalRecordAdapter } from "@/src/infrastructure"
import type { CreatePersonalRecord } from "@/src/domain/models"
import { toActionResult } from "./action-result"

const adapter = createPersonalRecordAdapter()

export async function createPersonalRecordAction(data: CreatePersonalRecord) {
  return toActionResult(adapter.create(data))
}

export async function getAllPersonalRecordsAction() {
  return toActionResult(adapter.getAll())
}

export async function getPersonalRecordsByExerciseAction(exerciseUuid: string) {
  return toActionResult(adapter.getByExercise(exerciseUuid))
}

export async function getPersonalRecordByExerciseAndTypeAction(
  exerciseUuid: string,
  recordType: string,
) {
  return toActionResult(adapter.getByExerciseAndType(exerciseUuid, recordType))
}

export async function getPersonalRecordAction(uuid: string) {
  return toActionResult(adapter.getByUuid(uuid))
}

export async function deletePersonalRecordAction(uuid: string) {
  return toActionResult(adapter.delete(uuid))
}
