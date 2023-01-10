package pool.dashboard

import pool.Context
import pool.Measurement

final class CalciumHardnessPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCalciumHardness
  range.text = context.dashboardCalciumHardnessRange
  good.text = context.dashboardCalciumHardnessGood
  ideal.text = context.dashboardCalciumHardnessIdeal
  current.text <== context.model.currentCalciumHardness.asString
  average.text <== context.model.averageCalciumHardness.asString

  context.model.currentCalciumHardness.onChange { (_, _, newValue) =>
    if context.model.calciumHardnessInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  context.model.averageCalciumHardness.onChange { (_, _, newValue) =>
    if context.model.calciumHardnessInRange(newValue) then inRangeAverage else outOfRangeAverage
  }