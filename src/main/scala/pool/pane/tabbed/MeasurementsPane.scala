package pool.pane.tabbed

import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, VBox}

import pool.Context
import pool.pane.AddEditToolbar

class MeasurementsPane(context: Context) extends VBox with AddEditToolbar(context):
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val label = new Label {
    text = context.labelMeasurements
  }

  children = List(label, toolbar)