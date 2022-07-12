package pool.dashboard

import pool.Context

class PhPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerPh
  current.text <== context.model.currentPh.asString
  average.text <== context.model.averagePh.asString