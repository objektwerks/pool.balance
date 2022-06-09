package pool.pane

import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.layout.TilePane

import pool.Context

class MenuPane(context: Context) extends TilePane:
  orientation = Orientation.Vertical
  alignment = Pos.Center