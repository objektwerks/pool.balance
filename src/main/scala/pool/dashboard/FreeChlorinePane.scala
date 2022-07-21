package pool.dashboard

import pool.Context
import pool.Measurement

class FreeChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerFreeChlorine
  range.text = context.dashboardFreeChlorineRange
  good.text = context.dashboardFreeChlorineGood
  ideal.text = context.dashboardFreeChlorineIdeal
  current.text <== context.model.currentFreeChlorine.asString
  average.text <== context.model.averageFreeChlorine.asString

  context.model.currentFreeChlorine.onChange { (_, _, newValue) =>
    if Measurement.freeChlorineRange.contains(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  context.model.averageFreeChlorine.onChange { (_, _, newValue) =>
    if Measurement.freeChlorineRange.contains(newValue) then inRangeAverage else outOfRangeAverage
  }