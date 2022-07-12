package pool.dashboard

import pool.Context

class FreeChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerFreeChlorine
  current.text <== context.model.currentFreeChlorine.asString
  average.text <== context.model.averageFreeChlorine.asString