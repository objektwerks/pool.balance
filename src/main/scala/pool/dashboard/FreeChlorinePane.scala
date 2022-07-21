package pool.dashboard

import pool.Context

class FreeChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerFreeChlorine
  range.text = context.dashboardFreeChlorineRange
  good.text = context.dashboardFreeChlorineGood
  ideal.text = context.dashboardFreeChlorineIdeal
  current.text <== context.model.currentFreeChlorine.asString
  average.text <== context.model.averageFreeChlorine.asString

  context.model.rangeCurrentFreeChlorine.onChange { (_, _, inRange) =>
    if inRange then inRangeCurrent else outOfRangeCurrent
  }

  context.model.rangeAverageFreeChlorine.onChange { (_, _, inRange) =>
    if inRange then inRangeAverage else outOfRangeAverage
  }