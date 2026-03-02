"use client"

import type { FormEvent } from "react"

import { useState } from "react"
import { useTranslations } from "next-intl"
import {
  Alert,
  Button,
  Card,
  Form,
  Input,
  Label,
  Spinner,
  TextField,
} from "@heroui/react"
import { Link, useRouter } from "@/src/i18n/navigation"
import { loginAction } from "@/src/actions"

export default function LoginPage() {
  const t = useTranslations("LoginPage")
  const tc = useTranslations("Common")
  const router = useRouter()
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setError(null)
    setLoading(true)

    try {
      const result = await loginAction({ email, password })

      if (result.success) {
        router.push("/")
        return
      }
      setError(result.error.message)
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
          <form onSubmit={(e) => {}}></form>
          <Form onSubmit={handleSubmit} className="space-y-4">
            {error && (
              <Alert status="danger">
                <Alert.Content>
                  <Alert.Description>{error}</Alert.Description>
                </Alert.Content>
              </Alert>
            )}
            <TextField
              name="email"
              type="email"
              isRequired
              isDisabled={loading}
            >
              <Label>{t("emailLabel")}</Label>
              <Input
                placeholder={t("emailPlaceholder")}
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                autoComplete="email"
              />
            </TextField>
            <TextField
              name="password"
              type="password"
              isRequired
              isDisabled={loading}
            >
              <Label>{t("passwordLabel")}</Label>
              <Input
                placeholder={t("passwordPlaceholder")}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                autoComplete="current-password"
              />
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
            {t("noAccount")}{" "}
            <Link href="/register" className="link font-medium">
              {t("signUp")}
            </Link>
          </p>
        </Card.Footer>
      </Card>
    </div>
  )
}
