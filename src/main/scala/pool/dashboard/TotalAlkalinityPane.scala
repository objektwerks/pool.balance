package pool.dashboard

import pool.Context

class TotalAlkalinityPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalAlkalinity
  range.text = context.dashboardTotalAlkalinityRange
  good.text = context.dashboardTotalAlkalinityGood
  ideal.text = context.dashboardTotalAlkalinityIdeal
  current.text <== context.model.currentTotalAlkalinity.asString
  average.text <== context.model.averageTotalAlkalinity.asString