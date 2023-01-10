package pool.dashboard

import pool.Context
import pool.Measurement

final class CyanuricAcidPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCyanuricAcid
  range.text = context.dashboardCyanuricAcidRange
  good.text = context.dashboardCyanuricAcidGood
  ideal.text = context.dashboardCyanuricAcidIdeal
  current.text <== context.model.currentCyanuricAcid.asString
  average.text <== context.model.averageCyanuricAcid.asString

  context.model.currentCyanuricAcid.onChange { (_, _, newValue) =>
    if context.model.cyanuricAcidInRange(newValue) then inRangeCurrent else outOfRangeCurrent
  }

  context.model.averageCyanuricAcid.onChange { (_, _, newValue) =>
    if context.model.cyanuricAcidInRange(newValue) then inRangeAverage else outOfRangeAverage
  }