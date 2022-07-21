package pool.dashboard

import pool.Context

class CyanuricAcidPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCyanuricAcid
  range.text = context.dashboardCyanuricAcidRange
  good.text = context.dashboardCyanuricAcidGood
  ideal.text = context.dashboardCyanuricAcidIdeal
  current.text <== context.model.currentCyanuricAcid.asString
  average.text <== context.model.averageCyanuricAcid.asString

  context.model.rangeCurrentCyanuricAcid.onChange { (_, _, inRange) =>
    if inRange then inRangeCurrent else outOfRangeCurrent
  }

  context.model.rangeAverageCyanuricAcid.onChange { (_, _, inRange) =>
    if inRange then inRangeAverage else outOfRangeAverage
  }