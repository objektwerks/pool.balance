package pool.dashboard

import pool.Context
import pool.Measurement

final class TotalAlkalinityPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalAlkalinity
  range.text = context.dashboardTotalAlkalinityRange
  good.text = context.dashboardTotalAlkalinityGood
  ideal.text = context.dashboardTotalAlkalinityIdeal
  current.text <== context.model.currentTotalAlkalinity.asString
  average.text <== context.model.averageTotalAlkalinity.asString

  context.model.currentTotalAlkalinity.onChange { (_, _, newValue) =>
    if context.model.totalAlkalinityInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  context.model.averageTotalAlkalinity.onChange { (_, _, newValue) =>
    if context.model.totalAlkalinityInRange(newValue) then inRangeCurrent else outOfRangeAverage
  }