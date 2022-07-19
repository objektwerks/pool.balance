package pool.dashboard

import pool.Context

class TotalAlkalinityPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalAlkalinity
  range.text = context.dashboardTotalAlkalinityRange
  good.text = context.dashboardTotalAlkalinityGood
  ideal.text = context.dashboardTotalAlkalinityIdeal
  current.text <== context.model.currentTotalAlkalinity.asString
  average.text <== context.model.averageTotalAlkalinity.asString

  context.model.inRangeAverageTotalAlkalinity.onChange { (_, _, inRange) =>
    if inRange then println("total alkalinity in range") else println("total alkalinity out of range")
  }