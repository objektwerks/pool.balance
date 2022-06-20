package pool.pane.dashboard

import pool.Context

class CombinedChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.tableCombinedChlorine

  context.model.currentCombinedChlorine.onChange { (_, _, newValue) =>
    currentValue.text = newValue.toString
  }

  context.model.averageCombinedChlorine.onChange { (_, _, newValue) =>
    currentAverage.text = newValue.toString
  }