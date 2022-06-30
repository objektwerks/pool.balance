package pool.dashboard

import pool.Context

class PhPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerPh
  currentValue.text <== context.model.currentPh.asString
  currentAverage.text <== context.model.averagePh.asString