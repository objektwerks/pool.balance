package pool.pane.tabbed

import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, VBox}

import pool.Context

class MeasurementsPane(context: Context) extends VBox:
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val label = new Label {
    text = context.labelMeasurements
  }

  val addButton = new Button {
    text = context.paneAdd
  }

  val editButton = new Button {
    text = context.paneEdit
    disable = true
  }

  val toolBar = new HBox {
    spacing = 6
    children = List(addButton, editButton)
  }

  children = List(label, toolBar)