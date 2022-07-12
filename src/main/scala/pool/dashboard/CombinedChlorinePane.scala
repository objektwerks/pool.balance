package pool.dashboard

import pool.Context

class CombinedChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCombinedChlorine
  range.text = context.dashboardCombinedChlorineRange
  good.text = context.dashboardCombinedChlorineGood
  ideal.text = context.dashboardCombinedChlorineIdeal
  current.text <== context.model.currentCombinedChlorine.asString
  average.text <== context.model.averageCombinedChlorine.asString