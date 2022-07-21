package pool.dashboard

import pool.Context
import pool.Measurement

class TotalChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalChlorine
  range.text = context.dashboardTotalChlorineRange
  good.text = context.dashboardTotalChlorineGood
  ideal.text = context.dashboardTotalChlorineIdeal
  current.text <== context.model.currentTotalChlorine.asString
  average.text <== context.model.averageTotalChlorine.asString

  context.model.rangeCurrentTotalChlorine.onChange { (_, _, inRange) =>
    println(s"in range current total chlorine: $inRange")
    if inRange then inRangeCurrent else outOfRangeCurrent
  }

  context.model.rangeAverageTotalChlorine.onChange { (_, _, inRange) =>
    println(s"in range average total chlorine: $inRange")
    if inRange then inRangeAverage else outOfRangeAverage
  }