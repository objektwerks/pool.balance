package pool.dashboard

import pool.Context

class CyanuricAcidPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCyanuricAcid
  currentValue.text <== context.model.currentCyanuricAcid.asString
  currentAverage.text <== context.model.averageCyanuricAcid.asString