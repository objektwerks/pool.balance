package pool.pane.dashboard

import pool.Context

class SaltPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerSalt
  currentValue.text <== context.model.currentTotalBromine.asString
  currentAverage.text <== context.model.averageTotalBromine.asString