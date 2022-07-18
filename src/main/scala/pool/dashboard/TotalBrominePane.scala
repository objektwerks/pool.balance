package pool.dashboard

import pool.Context

class TotalBrominePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalBromine
  range.text = context.dashboardTotalBromineRange
  good.text = context.dashboardTotalBromineGood
  ideal.text = context.dashboardTotalBromineIdeal
  current.text <== context.model.currentTotalBromine.asString
  average.text <== context.model.averageTotalBromine.asString

  context.model.inRangeTotalBromine.onChange { (_, _, inRange) =>
    if inRange then println("total bromine in range") else println("total bromine out of range")
  }