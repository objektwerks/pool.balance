package pool.pane

import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.layout.TilePane

import pool.Context

class DashboardPane(context: Context) extends TilePane:
  orientation = Orientation.Horizontal
  alignment = Pos.Center