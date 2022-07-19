package pool.dashboard

import pool.Context

class CombinedChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCombinedChlorine
  range.text = context.dashboardCombinedChlorineRange
  good.text = context.dashboardCombinedChlorineGood
  ideal.text = context.dashboardCombinedChlorineIdeal
  current.text <== context.model.currentCombinedChlorine.asString
  average.text <== context.model.averageCombinedChlorine.asString

  context.model.inRangeCurrentCombinedChlorine.onChange { (_, _, inRange) =>
    if inRange then println("current combined chlorine in range") else println("current combined chlorine out of range")
  }

  context.model.inRangeAverageCombinedChlorine.onChange { (_, _, inRange) =>
    if inRange then println("average combined chlorine in range") else println("average combined chlorine out of range")
  }