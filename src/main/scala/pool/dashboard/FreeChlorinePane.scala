package pool.dashboard

import pool.Context

class FreeChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerFreeChlorine
  range.text = context.dashboardFreeChlorineRange
  ideal.text = context.dashboardFreeChlorineIdeal
  current.text <== context.model.currentFreeChlorine.asString
  average.text <== context.model.averageFreeChlorine.asString