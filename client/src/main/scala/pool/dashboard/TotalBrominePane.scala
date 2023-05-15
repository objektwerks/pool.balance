package pool.dashboard

import pool.{Context, Model}

final class TotalBrominePane(context: Context, model: Model) extends DashboardTitledPane(context):
  text = context.headerTotalBromine
  range.text = context.dashboardTotalBromineRange
  good.text = context.dashboardTotalBromineGood
  ideal.text = context.dashboardTotalBromineIdeal
  current.text <== model.currentTotalBromine.asString
  average.text <== model.averageTotalBromine.asString

  model.currentTotalBromine.onChange { (_, _, newValue) =>
    if model.totalBromineInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  model.averageTotalBromine.onChange { (_, _, newValue) =>
    if model.totalBromineInRange(newValue) then inRangeAverage else outOfRangeAverage
  }