package pool.dashboard

import pool.{Context, Model}

final class PhPane(context: Context, model: Model) extends DashboardTitledPane(context):
  text = context.headerPh
  range.text = context.dashboardPhRange
  good.text = context.dashboardPhGood
  ideal.text = context.dashboardPhIdeal
  current.text <== model.currentPh.asString
  average.text <== model.averagePh.asString

  model.currentPh.onChange { (_, _, newValue) =>
    if model.phInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  model.averagePh.onChange { (_, _, newValue) =>
    if model.phInRange(newValue) then inRangeAverage else outOfRangeAverage
  }