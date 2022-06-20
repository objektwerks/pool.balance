package pool.pane.dashboard

import pool.Context

class TotalBrominePane(context: Context) extends DashboardTitledPane(context):
  text = context.tableTotalBromine
  currentValue.text <== context.model.currentTotalBromine.asString
  currentAverage.text <== context.model.averageTotalBromine.asString