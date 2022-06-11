package pool.pane.tabbed

import scalafx.geometry.Insets
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox

import pool.Context

class MeasurementsPane(context: Context) extends VBox:
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val label = new Label {
    text = context.labelMeasurements
  }

  children = List(label)