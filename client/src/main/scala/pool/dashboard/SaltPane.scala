package pool.dashboard

import pool.{Context, Model}

final class SaltPane(context: Context, model: Model) extends DashboardTitledPane(context):
  text = context.headerSalt
  range.text = context.dashboardSaltRange
  good.text = context.dashboardSaltGood
  ideal.text = context.dashboardSaltIdeal
  current.text <== model.currentSalt.asString
  average.text <== model.averageSalt.asString

  model.currentSalt.onChange { (_, _, newValue) =>
    if model.saltInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  model.averageSalt.onChange { (_, _, newValue) =>
    if model.saltInRange(newValue) then inRangeAverage else outOfRangeAverage
  }