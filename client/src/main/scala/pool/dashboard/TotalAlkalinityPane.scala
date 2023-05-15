package pool.dashboard

import pool.{Context, Model}

final class TotalAlkalinityPane(context: Context, model: Model) extends DashboardTitledPane(context):
  text = context.headerTotalAlkalinity
  range.text = context.dashboardTotalAlkalinityRange
  good.text = context.dashboardTotalAlkalinityGood
  ideal.text = context.dashboardTotalAlkalinityIdeal
  current.text <== model.currentTotalAlkalinity.asString
  average.text <== model.averageTotalAlkalinity.asString

  model.currentTotalAlkalinity.onChange { (_, _, newValue) =>
    if model.totalAlkalinityInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  model.averageTotalAlkalinity.onChange { (_, _, newValue) =>
    if model.totalAlkalinityInRange(newValue) then inRangeCurrent else outOfRangeAverage
  }