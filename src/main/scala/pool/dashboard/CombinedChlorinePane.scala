package pool.dashboard

import pool.Context

class CombinedChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCombinedChlorine
  range.text = context.dashboardCombinedChlorineRange
  good.text = context.dashboardCombinedChlorineGood
  ideal.text = context.dashboardCombinedChlorineIdeal
  current.text <== context.model.currentCombinedChlorine.asString
  average.text <== context.model.averageCombinedChlorine.asString

  context.model.rangeCurrentCombinedChlorine.onChange { (_, _, inRange) =>
    println(s"in range current combined chlorine: $inRange")
    if inRange then inRangeCurrent else outOfRangeCurrent
  }

  context.model.rangeAverageCombinedChlorine.onChange { (_, _, inRange) =>
    println(s"in range average combined chlorine: $inRange")
    if inRange then inRangeAverage else outOfRangeAverage
  }