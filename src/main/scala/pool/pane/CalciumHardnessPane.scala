package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.control.TitledPane

import pool.Context

class CalciumHardnessPane(context: Context) extends TitledPane:
  collapsible = false
  padding = Insets(6)
  text = context.tableHeaderCalciumHardness