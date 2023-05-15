package pool.dashboard

import pool.{Context, Model}

final class CalciumHardnessPane(context: Context, model: Model) extends DashboardTitledPane(context):
  text = context.headerCalciumHardness
  range.text = context.dashboardCalciumHardnessRange
  good.text = context.dashboardCalciumHardnessGood
  ideal.text = context.dashboardCalciumHardnessIdeal
  current.text <== model.currentCalciumHardness.asString
  average.text <== model.averageCalciumHardness.asString

  model.currentCalciumHardness.onChange { (_, _, newValue) =>
    if model.calciumHardnessInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  model.averageCalciumHardness.onChange { (_, _, newValue) =>
    if model.calciumHardnessInRange(newValue) then inRangeAverage else outOfRangeAverage
  }