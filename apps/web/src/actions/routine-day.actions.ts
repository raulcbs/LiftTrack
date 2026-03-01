"use server"

import { createRoutineDayAdapter } from "@/src/infrastructure"
import type { CreateRoutineDay } from "@/src/domain/models"
import { toActionResult } from "./action-result"

const adapter = createRoutineDayAdapter()

export async function createRoutineDayAction(
  routineUuid: string,
  data: CreateRoutineDay,
) {
  return toActionResult(adapter.create(routineUuid, data))
}

export async function getRoutineDaysAction(routineUuid: string) {
  return toActionResult(adapter.getByRoutine(routineUuid))
}

export async function getRoutineDayAction(routineUuid: string, uuid: string) {
  return toActionResult(adapter.getByUuid(routineUuid, uuid))
}

export async function deleteRoutineDayAction(
  routineUuid: string,
  uuid: string,
) {
  return toActionResult(adapter.delete(routineUuid, uuid))
}
