package pool.pane.dashboard

import pool.Context

class TotalAlkalinityPane(context: Context) extends DashboardTitledPane(context):
  text = context.tableTotalAlkalinity
  currentValue.text <== context.model.currentTotalAlkalinity.asString
  currentAverage.text <== context.model.averageTotalAlkalinity.asString