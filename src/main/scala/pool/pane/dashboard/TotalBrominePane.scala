package pool.pane.dashboard

import pool.Context

class TotalBrominePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalBromine
  currentValue.text <== context.model.currentTotalBromine.asString
  currentAverage.text <== context.model.averageTotalBromine.asString