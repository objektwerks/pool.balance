package pool.dashboard

import pool.Context
import pool.Measurement

final class CombinedChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCombinedChlorine
  range.text = context.dashboardCombinedChlorineRange
  good.text = context.dashboardCombinedChlorineGood
  ideal.text = context.dashboardCombinedChlorineIdeal
  current.text <== context.model.currentCombinedChlorine.asString
  average.text <== context.model.averageCombinedChlorine.asString

  context.model.currentCombinedChlorine.onChange { (_, oldValue, newValue) =>
    if context.model.combinedChlorineInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  context.model.averageCombinedChlorine.onChange { (_, oldValue, newValue) =>
    if context.model.combinedChlorineInRange(newValue) then inRangeAverage else outOfRangeAverage
  }