"use client"

import type { FormEvent } from "react"

import { useState } from "react"
import { useTranslations } from "next-intl"
import {
  Alert,
  Button,
  Card,
  FieldError,
  Form,
  Input,
  Label,
  Spinner,
  TextField,
} from "@heroui/react"
import { Link, useRouter } from "@/src/i18n/navigation"
import { registerAction } from "@/src/actions"

export default function RegisterPage() {
  const t = useTranslations("RegisterPage")
  const tc = useTranslations("Common")
  const router = useRouter()
  const [name, setName] = useState("")
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [confirmPassword, setConfirmPassword] = useState("")
  const [error, setError] = useState<string | null>(null)
  const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({})
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setError(null)
    setFieldErrors({})

    if (password !== confirmPassword) {
      setFieldErrors({ confirmPassword: t("passwordsMismatch") })
      return
    }

    setLoading(true)

    try {
      const result = await registerAction({ name, email, password })

      if (result.success) {
        router.push("/")
        return
      }

      if (result.error.fields) {
        setFieldErrors(result.error.fields)
      } else {
        setError(result.error.message)
      }
    } catch {
      setError(tc("unexpectedError"))
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="flex min-h-screen items-center justify-center px-4">
      <Card className="w-full max-w-md">
        <Card.Header className="text-center">
          <Card.Title>{t("title")}</Card.Title>
          <Card.Description>{t("description")}</Card.Description>
        </Card.Header>
        <Card.Content>
          <Form onSubmit={handleSubmit} className="space-y-4">
            {error && (
              <Alert status="danger">
                <Alert.Content>
                  <Alert.Description>{error}</Alert.Description>
                </Alert.Content>
              </Alert>
            )}
            <TextField
              name="name"
              isRequired
              isDisabled={loading}
              isInvalid={!!fieldErrors.name}
            >
              <Label>{t("nameLabel")}</Label>
              <Input
                placeholder={t("namePlaceholder")}
                value={name}
                onChange={(e) => setName(e.target.value)}
                autoComplete="name"
              />
              {fieldErrors.name && <FieldError>{fieldErrors.name}</FieldError>}
            </TextField>
            <TextField
              name="email"
              type="email"
              isRequired
              isDisabled={loading}
              isInvalid={!!fieldErrors.email}
            >
              <Label>{t("emailLabel")}</Label>
              <Input
                placeholder={t("emailPlaceholder")}
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                autoComplete="email"
              />
              {fieldErrors.email && (
                <FieldError>{fieldErrors.email}</FieldError>
              )}
            </TextField>
            <TextField
              name="password"
              type="password"
              isRequired
              isDisabled={loading}
              isInvalid={!!fieldErrors.password}
            >
              <Label>{t("passwordLabel")}</Label>
              <Input
                placeholder={t("passwordPlaceholder")}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                autoComplete="new-password"
              />
              {fieldErrors.password && (
                <FieldError>{fieldErrors.password}</FieldError>
              )}
            </TextField>
            <TextField
              name="confirmPassword"
              type="password"
              isRequired
              isDisabled={loading}
              isInvalid={!!fieldErrors.confirmPassword}
            >
              <Label>{t("confirmPasswordLabel")}</Label>
              <Input
                placeholder={t("confirmPasswordPlaceholder")}
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                autoComplete="new-password"
              />
              {fieldErrors.confirmPassword && (
                <FieldError>{fieldErrors.confirmPassword}</FieldError>
              )}
            </TextField>
            <Button
              type="submit"
              fullWidth
              isDisabled={loading}
              isPending={loading}
            >
              {loading && <Spinner size="sm" />}
              {t("submit")}
            </Button>
          </Form>
        </Card.Content>
        <Card.Footer className="justify-center">
          <p className="text-muted text-center text-sm">
            {t("hasAccount")}{" "}
            <Link href="/login" className="link font-medium">
              {t("signIn")}
            </Link>
          </p>
        </Card.Footer>
      </Card>
    </div>
  )
}
