import { useTranslations } from "next-intl"

export default function DashboardPage() {
  const t = useTranslations("Dashboard")

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-3xl font-bold tracking-tight">{t("title")}</h2>
        <p className="text-muted-foreground">{t("subtitle")}</p>
      </div>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
        <div className="bg-card text-card-foreground rounded-lg border p-6 shadow-sm">
          <h3 className="text-muted-foreground text-sm font-medium">
            {t("workouts")}
          </h3>
          <p className="mt-2 text-2xl font-bold">--</p>
        </div>
        <div className="bg-card text-card-foreground rounded-lg border p-6 shadow-sm">
          <h3 className="text-muted-foreground text-sm font-medium">
            {t("exercises")}
          </h3>
          <p className="mt-2 text-2xl font-bold">--</p>
        </div>
        <div className="bg-card text-card-foreground rounded-lg border p-6 shadow-sm">
          <h3 className="text-muted-foreground text-sm font-medium">
            {t("personalRecords")}
          </h3>
          <p className="mt-2 text-2xl font-bold">--</p>
        </div>
      </div>
    </div>
  )
}
