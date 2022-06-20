package pool.pane.dashboard

import pool.Context

class CalciumHardnessPane(context: Context) extends DashboardTitledPane(context):
  text = context.tableCalciumHardness
  currentValue.text <== context.model.currentCalciumHardness.asString
  currentAverage.text <== context.model.averageCalciumHardness.asString