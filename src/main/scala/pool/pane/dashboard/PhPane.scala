package pool.pane.dashboard

import pool.Context

class PhPane(context: Context) extends DashboardTitledPane(context):
  text = context.tablePh
  currentValue.text <== context.model.currentPh.asString
  currentAverage.text <== context.model.averagePh.asString