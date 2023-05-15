package pool.dashboard

import pool.{Context, Model}

final class CyanuricAcidPane(context: Context, model: Model) extends DashboardTitledPane(context):
  text = context.headerCyanuricAcid
  range.text = context.dashboardCyanuricAcidRange
  good.text = context.dashboardCyanuricAcidGood
  ideal.text = context.dashboardCyanuricAcidIdeal
  current.text <== model.currentCyanuricAcid.asString
  average.text <== model.averageCyanuricAcid.asString

  model.currentCyanuricAcid.onChange { (_, _, newValue) =>
    if model.cyanuricAcidInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  model.averageCyanuricAcid.onChange { (_, _, newValue) =>
    if model.cyanuricAcidInRange(newValue) then inRangeAverage else outOfRangeAverage
  }