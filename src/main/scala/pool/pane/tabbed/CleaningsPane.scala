package pool.pane.tabbed

import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, VBox}

import pool.Context
import pool.pane.AddEditToolbar

class CleaningsPane(context: Context) extends VBox with AddEditToolbar(context):
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val label = new Label {
    text = context.labelCleanings
  }

  children = List(label, toolbar)