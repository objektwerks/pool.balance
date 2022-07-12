package pool.dashboard

import pool.Context

class TotalChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalChlorine
  range.text = ""
  ideal.text = ""
  current.text <== context.model.currentTotalChlorine.asString
  average.text <== context.model.averageTotalChlorine.asString