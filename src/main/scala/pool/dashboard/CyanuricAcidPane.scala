package pool.dashboard

import pool.Context

class CyanuricAcidPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCyanuricAcid
  range.text = context.dashboardCyanuricAcidRange
  good.text = context.dashboardCyanuricAcidGood
  ideal.text = context.dashboardCyanuricAcidIdeal
  current.text <== context.model.currentCyanuricAcid.asString
  average.text <== context.model.averageCyanuricAcid.asString