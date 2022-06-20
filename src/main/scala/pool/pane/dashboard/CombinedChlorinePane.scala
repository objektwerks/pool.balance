package pool.pane.dashboard

import pool.Context

class CombinedChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.tableCombinedChlorine
  currentValue.text <== context.model.currentCombinedChlorine.asString
  currentAverage.text <== context.model.averageCombinedChlorine.asString