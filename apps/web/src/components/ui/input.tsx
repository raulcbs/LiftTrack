"use client"

import { type ComponentProps } from "react"
import { cn } from "@/src/lib/utils"

interface InputProps extends ComponentProps<"input"> {}

export function Input({ className, type, ...props }: InputProps) {
  return (
    <input
      type={type}
      className={cn(
        "border-input bg-background flex h-10 w-full rounded-md border px-3 py-2 text-sm",
        "file:border-0 file:bg-transparent file:text-sm file:font-medium",
        "placeholder:text-muted-foreground",
        "focus-visible:ring-ring focus-visible:ring-offset-background focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:outline-none",
        "disabled:cursor-not-allowed disabled:opacity-50",
        className,
      )}
      {...props}
    />
  )
}
