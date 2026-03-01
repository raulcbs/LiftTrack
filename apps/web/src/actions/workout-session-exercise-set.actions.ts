"use server"

import { createWorkoutSessionExerciseSetAdapter } from "@/src/infrastructure"
import type { CreateWorkoutSessionExerciseSet } from "@/src/domain/models"
import { toActionResult } from "./action-result"

const adapter = createWorkoutSessionExerciseSetAdapter()

export async function createExerciseSetAction(
  workoutSessionExerciseUuid: string,
  data: CreateWorkoutSessionExerciseSet,
) {
  return toActionResult(adapter.create(workoutSessionExerciseUuid, data))
}

export async function getExerciseSetsAction(
  workoutSessionExerciseUuid: string,
) {
  return toActionResult(adapter.getByExercise(workoutSessionExerciseUuid))
}

export async function getExerciseSetAction(
  workoutSessionExerciseUuid: string,
  uuid: string,
) {
  return toActionResult(adapter.getByUuid(workoutSessionExerciseUuid, uuid))
}

export async function deleteExerciseSetAction(
  workoutSessionExerciseUuid: string,
  uuid: string,
) {
  return toActionResult(adapter.delete(workoutSessionExerciseUuid, uuid))
}
