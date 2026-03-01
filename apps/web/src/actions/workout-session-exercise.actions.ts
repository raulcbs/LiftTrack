"use server"

import { createWorkoutSessionExerciseAdapter } from "@/src/infrastructure"
import type { CreateWorkoutSessionExercise } from "@/src/domain/models"
import { toActionResult } from "./action-result"

const adapter = createWorkoutSessionExerciseAdapter()

export async function createSessionExerciseAction(
  workoutSessionUuid: string,
  data: CreateWorkoutSessionExercise,
) {
  return toActionResult(adapter.create(workoutSessionUuid, data))
}

export async function getSessionExercisesAction(workoutSessionUuid: string) {
  return toActionResult(adapter.getBySession(workoutSessionUuid))
}

export async function getSessionExerciseAction(
  workoutSessionUuid: string,
  uuid: string,
) {
  return toActionResult(adapter.getByUuid(workoutSessionUuid, uuid))
}

export async function deleteSessionExerciseAction(
  workoutSessionUuid: string,
  uuid: string,
) {
  return toActionResult(adapter.delete(workoutSessionUuid, uuid))
}
