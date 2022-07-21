package pool.dashboard

import pool.Context
import pool.Measurement

final class SaltPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerSalt
  range.text = context.dashboardSaltRange
  good.text = context.dashboardSaltGood
  ideal.text = context.dashboardSaltIdeal
  current.text <== context.model.currentSalt.asString
  average.text <== context.model.averageSalt.asString

  context.model.currentSalt.onChange { (_, _, newValue) =>
    if context.model.saltInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  context.model.averageSalt.onChange { (_, _, newValue) =>
    if context.model.saltInRange(newValue) then inRangeAverage else outOfRangeAverage
  }