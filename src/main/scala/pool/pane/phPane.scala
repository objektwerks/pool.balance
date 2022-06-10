package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox

import pool.Context

class PhPane(context: Context) extends VBox:
  spacing = 6
  padding = Insets(6)

  val title = new Label {
    text = context.tableHeaderPh
  }

  children = List(title)