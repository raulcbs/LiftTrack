"use server"

import { createRoutineExerciseAdapter } from "@/src/infrastructure"
import type { CreateRoutineExercise } from "@/src/domain/models"
import { toActionResult } from "./action-result"

const adapter = createRoutineExerciseAdapter()

export async function createRoutineExerciseAction(
  routineDayUuid: string,
  data: CreateRoutineExercise,
) {
  return toActionResult(adapter.create(routineDayUuid, data))
}

export async function getRoutineExercisesAction(routineDayUuid: string) {
  return toActionResult(adapter.getByRoutineDay(routineDayUuid))
}

export async function getRoutineExerciseAction(
  routineDayUuid: string,
  uuid: string,
) {
  return toActionResult(adapter.getByUuid(routineDayUuid, uuid))
}

export async function deleteRoutineExerciseAction(
  routineDayUuid: string,
  uuid: string,
) {
  return toActionResult(adapter.delete(routineDayUuid, uuid))
}
