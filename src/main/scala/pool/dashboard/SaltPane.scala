package pool.dashboard

import pool.Context

class SaltPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerSalt
  range.text = context.dashboardSaltRange
  good.text = context.dashboardSaltGood
  ideal.text = context.dashboardSaltIdeal
  current.text <== context.model.currentSalt.asString
  average.text <== context.model.averageSalt.asString

  context.model.rangeCurrentSalt.onChange { (_, _, inRange) =>
    if inRange then inRangeCurrent else outOfRangeCurrent
  }

  context.model.rangeAverageSalt.onChange { (_, _, inRange) =>
    if inRange then inRangeAverage else outOfRangeAverage
  }