package pool.dashboard

import pool.Context
import pool.Measurement

final class TotalChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalChlorine
  range.text = context.dashboardTotalChlorineRange
  good.text = context.dashboardTotalChlorineGood
  ideal.text = context.dashboardTotalChlorineIdeal
  current.text <== context.model.currentTotalChlorine.asString
  average.text <== context.model.averageTotalChlorine.asString

  context.model.currentTotalChlorine.onChange { (_, _, newValue) =>
    if context.model.totalChlorineInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  context.model.averageTotalChlorine.onChange { (_, _, newValue) =>
    if context.model.totalChlorineInRange(newValue) then inRangeAverage else outOfRangeAverage
  }