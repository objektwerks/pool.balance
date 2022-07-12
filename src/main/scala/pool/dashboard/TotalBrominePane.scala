package pool.dashboard

import pool.Context

class TotalBrominePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalBromine
  current.text <== context.model.currentTotalBromine.asString
  average.text <== context.model.averageTotalBromine.asString