package pool.dashboard

import pool.Context

class TotalChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalChlorine
  range.text = context.dashboardTotalChlorineRange
  ideal.text = context.dashboardTotalChlorineIdeal
  current.text <== context.model.currentTotalChlorine.asString
  average.text <== context.model.averageTotalChlorine.asString