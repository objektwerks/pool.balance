package pool.dashboard

import pool.Context
import pool.Measurement

final class TotalBrominePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerTotalBromine
  range.text = context.dashboardTotalBromineRange
  good.text = context.dashboardTotalBromineGood
  ideal.text = context.dashboardTotalBromineIdeal
  current.text <== context.model.currentTotalBromine.asString
  average.text <== context.model.averageTotalBromine.asString

  context.model.currentTotalBromine.onChange { (_, _, newValue) =>
    if context.model.totalBromineInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  context.model.averageTotalBromine.onChange { (_, _, newValue) =>
    if context.model.totalBromineInRange(newValue) then inRangeAverage else outOfRangeAverage
  }