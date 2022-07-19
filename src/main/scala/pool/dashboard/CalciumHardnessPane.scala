package pool.dashboard

import pool.Context

class CalciumHardnessPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCalciumHardness
  range.text = context.dashboardCalciumHardnessRange
  good.text = context.dashboardCalciumHardnessGood
  ideal.text = context.dashboardCalciumHardnessIdeal
  current.text <== context.model.currentCalciumHardness.asString
  average.text <== context.model.averageCalciumHardness.asString

  context.model.inRangeCurrentCalciumHardness.onChange { (_, _, inRange) =>
    if inRange then println("current calcium hardness in range") else println("current calcium hardness out of range")
  }

  context.model.inRangeAverageCalciumHardness.onChange { (_, _, inRange) =>
    if inRange then println("average calcium hardness in range") else println("average calcium hardness out of range")
  }