package pool.pane.dashboard

import pool.Context

class TotalAlkalinityPane(context: Context) extends DashboardTitledPane(context):
  text = context.tableTotalAlkalinity

  context.model.currentTotalAlkalinity.onChange { (_, _, newValue) =>
    currentValue.text = newValue.toString
  }

  context.model.averageTotalAlkalinity.onChange { (_, _, newValue) =>
    currentAverage.text = newValue.toString
  }