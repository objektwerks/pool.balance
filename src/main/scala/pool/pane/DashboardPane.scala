package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.layout.VBox

import pool.Context

class DashboardPane(context: Context) extends VBox:
  spacing = 6
  padding = Insets(6)

  children = List(FreeChlorinePane(context),
                  PhPane(context),
                  TotalAlkalinityPane(context),
                  CalciumHardnessPane(context))