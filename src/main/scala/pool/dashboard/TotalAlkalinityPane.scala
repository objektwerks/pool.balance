package pool.dashboard

import pool.Context

class TotalAlkalinityPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalAlkalinity
  range.text = context.dashboardTotalAlkalinityRange
  good.text = context.dashboardTotalAlkalinityGood
  ideal.text = context.dashboardTotalAlkalinityIdeal
  current.text <== context.model.currentTotalAlkalinity.asString
  average.text <== context.model.averageTotalAlkalinity.asString

  context.model.rangeCurrentTotalAlkalinity.onChange { (_, _, inRange) =>
    if inRange then println("current total alkalinity in range") else println("current total alkalinity out of range")
  }

  context.model.rangeAverageTotalAlkalinity.onChange { (_, _, inRange) =>
    if inRange then println("average total alkalinity in range") else println("average total alkalinity out of range")
  }