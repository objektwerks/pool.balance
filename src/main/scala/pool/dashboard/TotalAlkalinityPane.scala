package pool.dashboard

import pool.Context

class TotalAlkalinityPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalAlkalinity
  currentValue.text <== context.model.currentTotalAlkalinity.asString
  currentAverage.text <== context.model.averageTotalAlkalinity.asString