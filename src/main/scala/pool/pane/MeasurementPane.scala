package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.layout.VBox

import pool.Context

class MeasurementPane(context: Context) extends VBox:
  spacing = 6
  padding = Insets(6)

  val model = context.model