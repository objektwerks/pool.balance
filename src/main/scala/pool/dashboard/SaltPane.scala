package pool.dashboard

import pool.Context

class SaltPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerSalt
  range.text = context.dashboardSaltRange
  good.text = context.dashboardSaltGood
  ideal.text = context.dashboardSaltIdeal
  current.text <== context.model.currentSalt.asString
  average.text <== context.model.averageSalt.asString

  context.model.inRangeSalt.onChange { (_, _, inRange) =>
    if inRange then println("salt in range") else println("salt out of range")
  }