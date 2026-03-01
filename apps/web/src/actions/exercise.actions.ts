"use server"

import { createExerciseAdapter } from "@/src/infrastructure"
import type { CreateExercise } from "@/src/domain/models"
import { toActionResult } from "./action-result"

const adapter = createExerciseAdapter()

export async function createExerciseAction(data: CreateExercise) {
  return toActionResult(adapter.create(data))
}

export async function getExerciseAction(uuid: string) {
  return toActionResult(adapter.getByUuid(uuid))
}

export async function getGlobalExercisesAction() {
  return toActionResult(adapter.getGlobal())
}

export async function getExercisesByMuscleGroupAction(muscleGroupUuid: string) {
  return toActionResult(adapter.getByMuscleGroup(muscleGroupUuid))
}

export async function getLastSetAction(exerciseUuid: string) {
  return toActionResult(adapter.getLastSet(exerciseUuid))
}

export async function deleteExerciseAction(uuid: string) {
  return toActionResult(adapter.delete(uuid))
}
