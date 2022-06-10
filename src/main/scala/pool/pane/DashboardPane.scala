package pool.pane

import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.layout.GridPane

import pool.Context

class DashboardPane(context: Context) extends GridPane:
  alignment = Pos.Center
  padding = Insets(6)

  add(FreeChlorinePane(context), columnIndex = 0, rowIndex = 1)
  add(PhPane(context), columnIndex = 0, rowIndex = 2)
  add(TotalAlkalinityPane(context), columnIndex = 0, rowIndex = 3)
  add(CalciumHardnessPane(context), columnIndex = 0, rowIndex = 4)