import { Card } from "@heroui/react"
import { useTranslations } from "next-intl"

export default function DashboardPage() {
  const t = useTranslations("Dashboard")

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-3xl font-bold tracking-tight">{t("title")}</h2>
        <p className="text-muted">{t("subtitle")}</p>
      </div>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
        <Card>
          <Card.Header>
            <Card.Description>{t("workouts")}</Card.Description>
          </Card.Header>
          <Card.Content>
            <p className="text-2xl font-bold">--</p>
          </Card.Content>
        </Card>
        <Card>
          <Card.Header>
            <Card.Description>{t("exercises")}</Card.Description>
          </Card.Header>
          <Card.Content>
            <p className="text-2xl font-bold">--</p>
          </Card.Content>
        </Card>
        <Card>
          <Card.Header>
            <Card.Description>{t("personalRecords")}</Card.Description>
          </Card.Header>
          <Card.Content>
            <p className="text-2xl font-bold">--</p>
          </Card.Content>
        </Card>
      </div>
    </div>
  )
}
