package pool.dashboard

import pool.Context
import pool.Measurement

class CombinedChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCombinedChlorine
  range.text = context.dashboardCombinedChlorineRange
  good.text = context.dashboardCombinedChlorineGood
  ideal.text = context.dashboardCombinedChlorineIdeal
  current.text <== context.model.currentCombinedChlorine.asString
  average.text <== context.model.averageCombinedChlorine.asString

  context.model.currentCombinedChlorine.onChange { (_, oldValue, newValue) =>
    println(s"current combined chlorine: $oldValue -> $newValue")
    if Measurement.combinedChlorineRange.contains(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  context.model.averageCombinedChlorine.onChange { (_, oldValue, newValue) =>
    println(s"average combined chlorine: $oldValue -> $newValue")
    if Measurement.combinedChlorineRange.contains(newValue) then inRangeAverage else outOfRangeAverage
  }