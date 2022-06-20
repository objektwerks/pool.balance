package pool.pane.dashboard

import pool.Context

class CalciumHardnessPane(context: Context) extends DashboardTitledPane(context):
  text = context.tableCalciumHardness

  context.model.currentCalciumHardness.onChange { (_, _, newValue) =>
    currentValue.text = newValue.toString
  }

  context.model.averageCalciumHardness.onChange { (_, _, newValue) =>
    currentAverage.text = newValue.toString
  }