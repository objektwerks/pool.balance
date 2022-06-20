package pool.pane.dashboard

import pool.Context

class PhPane(context: Context) extends DashboardTitledPane(context):
  text = context.tablePh

  context.model.currentPh.onChange { (_, _, newValue) =>
    currentValue.text = newValue.toString
  }

  context.model.averagePh.onChange { (_, _, newValue) =>
    currentAverage.text = newValue.toString
  }