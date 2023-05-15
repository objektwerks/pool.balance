package pool.dashboard

import pool.{Context, Model}

final class CombinedChlorinePane(context: Context, model: Model) extends DashboardTitledPane(context):
  text = context.headerCombinedChlorine
  range.text = context.dashboardCombinedChlorineRange
  good.text = context.dashboardCombinedChlorineGood
  ideal.text = context.dashboardCombinedChlorineIdeal
  current.text <== model.currentCombinedChlorine.asString
  average.text <== model.averageCombinedChlorine.asString

  model.currentCombinedChlorine.onChange { (_, oldValue, newValue) =>
    if model.combinedChlorineInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  model.averageCombinedChlorine.onChange { (_, oldValue, newValue) =>
    if model.combinedChlorineInRange(newValue) then inRangeAverage else outOfRangeAverage
  }