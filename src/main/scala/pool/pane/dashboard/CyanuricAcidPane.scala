package pool.pane.dashboard

import pool.Context

class CyanuricAcidPane(context: Context) extends DashboardTitledPane(context):
  text = context.tableCyanuricAcid

  context.model.currentCyanuricAcid.onChange { (_, _, newValue) =>
    currentValue.text = newValue.toString
  }

  context.model.averageCyanuricAcid.onChange { (_, _, newValue) =>
    currentAverage.text = newValue.toString
  }