package pool.dashboard

import pool.Context

class CalciumHardnessPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerCalciumHardness
  range.text = context.dashboardCalciumHardnessRange
  ideal.text = context.dashboardCalciumHardnessIdeal
  current.text <== context.model.currentCalciumHardness.asString
  average.text <== context.model.averageCalciumHardness.asString