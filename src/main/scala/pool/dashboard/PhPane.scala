package pool.dashboard

import pool.Context

class PhPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerPh
  range.text = context.dashboardPhRange
  good.text = context.dashboardPhGood
  ideal.text = context.dashboardPhIdeal
  current.text <== context.model.currentPh.asString
  average.text <== context.model.averagePh.asString

  context.model.inRangeCurrentPh.onChange { (_, _, inRange) =>
    if inRange then println("current ph in range") else println("current ph out of range")
  }

  context.model.inRangeAveragePh.onChange { (_, _, inRange) =>
    if inRange then println("average ph in range") else println("average ph out of range")
  }