package pool.dashboard

import pool.Context

class TotalBrominePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalBromine
  range.text = context.dashboardTotalBromineRange
  good.text = context.dashboardTotalBromineGood
  ideal.text = context.dashboardTotalBromineIdeal
  current.text <== context.model.currentTotalBromine.asString
  average.text <== context.model.averageTotalBromine.asString

  context.model.rangeCurrentTotalBromine.onChange { (_, _, inRange) =>
    if inRange then println("current total bromine in range") else println("current total bromine out of range")
  }

  context.model.rangeAverageTotalBromine.onChange { (_, _, inRange) =>
    if inRange then println("average total bromine in range") else println("average total bromine out of range")
  }