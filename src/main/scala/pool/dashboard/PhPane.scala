package pool.dashboard

import pool.Context
import pool.Measurement

class PhPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerPh
  range.text = context.dashboardPhRange
  good.text = context.dashboardPhGood
  ideal.text = context.dashboardPhIdeal
  current.text <== context.model.currentPh.asString
  average.text <== context.model.averagePh.asString

  context.model.currentPh.onChange { (_, _, newValue) =>
    if Measurement.phRange.contains(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  context.model.averagePh.onChange { (_, _, newValue) =>
    if Measurement.phRange.contains(newValue) then inRangeAverage else outOfRangeAverage
  }