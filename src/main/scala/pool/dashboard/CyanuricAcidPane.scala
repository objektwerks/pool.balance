package pool.dashboard

import pool.Context

class CyanuricAcidPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCyanuricAcid
  current.text <== context.model.currentCyanuricAcid.asString
  average.text <== context.model.averageCyanuricAcid.asString