package pool.dashboard

import pool.Context

class FreeChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.headerFreeChlorine
  range.text = context.dashboardFreeChlorineRange
  good.text = context.dashboardFreeChlorineGood
  ideal.text = context.dashboardFreeChlorineIdeal
  current.text <== context.model.currentFreeChlorine.asString
  average.text <== context.model.averageFreeChlorine.asString

  context.model.inRangeCurrentFreeChlorine.onChange { (_, _, inRange) =>
    if inRange then println("current free chlorine in range") else println("current free chlorine out of range")
  }

  context.model.inRangeAverageFreeChlorine.onChange { (_, _, inRange) =>
    if inRange then println("average free chlorine in range") else println("average free chlorine out of range")
  }