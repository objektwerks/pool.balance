package pool.dashboard

import pool.Context

class CalciumHardnessPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCalciumHardness
  range.text = context.dashboardCalciumHardnessRange
  good.text = context.dashboardCalciumHardnessGood
  ideal.text = context.dashboardCalciumHardnessIdeal
  current.text <== context.model.currentCalciumHardness.asString
  average.text <== context.model.averageCalciumHardness.asString

  context.model.rangeCurrentCalciumHardness.onChange { (_, _, inRange) =>
    if inRange then inRangeCurrent else outOfRangeCurrent
  }

  context.model.rangeAverageCalciumHardness.onChange { (_, _, inRange) =>
    if inRange then inRangeAverage else outOfRangeAverage
  }