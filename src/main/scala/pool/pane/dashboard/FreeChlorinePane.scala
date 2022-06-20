package pool.pane.dashboard

import pool.Context

class FreeChlorinePane(context: Context) extends DashboardTitledPane(context):
  text = context.tableFreeChlorine

  context.model.currentFreeChlorine.onChange { (_, _, newValue) =>
    currentValue.text = newValue.toString
  }

  context.model.averageFreeChlorine.onChange { (_, _, newValue) =>
    currentAverage.text = newValue.toString
  }