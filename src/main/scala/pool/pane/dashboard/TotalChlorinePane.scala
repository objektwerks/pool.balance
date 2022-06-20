package pool.pane.dashboard

import pool.Context

class TotalChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.tableTotalChlorine

  context.model.currentTotalChlorine.onChange { (_, _, newValue) =>
    currentValue.text = newValue.toString
  }

  context.model.averageTotalChlorine.onChange { (_, _, newValue) =>
    currentAverage.text = newValue.toString
  }