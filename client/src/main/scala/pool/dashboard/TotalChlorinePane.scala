package pool.dashboard

import pool.{Context, Model}

final class TotalChlorinePane(context: Context, model: Model) extends DashboardTitledPane(context):
  text = context.headerTotalChlorine
  range.text = context.dashboardTotalChlorineRange
  good.text = context.dashboardTotalChlorineGood
  ideal.text = context.dashboardTotalChlorineIdeal
  current.text <== model.currentTotalChlorine.asString
  average.text <== model.averageTotalChlorine.asString

  model.currentTotalChlorine.onChange { (_, _, newValue) =>
    if model.totalChlorineInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  model.averageTotalChlorine.onChange { (_, _, newValue) =>
    if model.totalChlorineInRange(newValue) then inRangeAverage else outOfRangeAverage
  }