package pool.dashboard

import pool.{Context, Model}

final class FreeChlorinePane(context: Context, model: Model) extends DashboardTitledPane(context):
  text = context.headerFreeChlorine
  range.text = context.dashboardFreeChlorineRange
  good.text = context.dashboardFreeChlorineGood
  ideal.text = context.dashboardFreeChlorineIdeal
  current.text <== model.currentFreeChlorine.asString
  average.text <== model.averageFreeChlorine.asString

  model.currentFreeChlorine.onChange { (_, _, newValue) =>
    if model.freeChlorineInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  model.averageFreeChlorine.onChange { (_, _, newValue) =>
    if model.freeChlorineInRange(newValue) then inRangeAverage else outOfRangeAverage
  }