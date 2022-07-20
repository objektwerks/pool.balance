package pool.dashboard

import pool.Context

class SaltPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerSalt
  range.text = context.dashboardSaltRange
  good.text = context.dashboardSaltGood
  ideal.text = context.dashboardSaltIdeal
  current.text <== context.model.currentSalt.asString
  average.text <== context.model.averageSalt.asString

  context.model.inRangeCurrentSalt.onChange { (_, _, inRange) =>
    if inRange then println("current salt in range") else println("current salt out of range")
  }

  context.model.inRangeAverageSalt.onChange { (_, _, inRange) =>
    if inRange then println("average salt in range") else println("average salt out of range")
  }