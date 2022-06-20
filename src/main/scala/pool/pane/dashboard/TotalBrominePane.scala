package pool.pane.dashboard

import pool.Context

class TotalBrominePane(context: Context) extends DashboardTitledPane(context):
  text = context.tableTotalBromine

  context.model.currentTotalBromine.onChange { (_, _, newValue) =>
    currentValue.text = newValue.toString
  }

  context.model.averageTotalBromine.onChange { (_, _, newValue) =>
    currentAverage.text = newValue.toString
  }