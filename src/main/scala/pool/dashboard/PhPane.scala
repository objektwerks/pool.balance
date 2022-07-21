package pool.dashboard

import pool.Context

class PhPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerPh
  range.text = context.dashboardPhRange
  good.text = context.dashboardPhGood
  ideal.text = context.dashboardPhIdeal
  current.text <== context.model.currentPh.asString
  average.text <== context.model.averagePh.asString

  context.model.rangeCurrentPh.onChange { (_, _, inRange) =>
    if inRange then inRangeCurrent else outOfRangeCurrent
  }

  context.model.rangeAveragePh.onChange { (_, _, inRange) =>
    if inRange then inRangeAverage else outOfRangeAverage
  }