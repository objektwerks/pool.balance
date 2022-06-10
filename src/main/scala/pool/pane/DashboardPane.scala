package pool.pane

import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.layout.TilePane

import pool.Context

class DashboardPane(context: Context) extends TilePane:
  orientation = Orientation.Horizontal
  alignment = Pos.Center
  padding = Insets(6)

  children = List(FreeChlorinePane(context),
                  phPane(context),
                  TotalAlkalinityPane(context),
                  CalciumHardnessPane(context))