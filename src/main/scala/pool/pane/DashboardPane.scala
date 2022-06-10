package pool.pane

import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.layout.TilePane

import pool.Context

class DashboardPane(context: Context) extends TilePane:
  orientation = Orientation.Vertical
  alignment = Pos.Center
  padding = Insets(6)
  prefRows = 4

  children = List(FreeChlorinePane(context),
                  PhPane(context),
                  TotalAlkalinityPane(context),
                  CalciumHardnessPane(context))