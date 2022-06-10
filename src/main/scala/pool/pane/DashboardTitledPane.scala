package pool.pane

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox

import pool.Context
import pool.dialog.ControlGridPane

abstract class DashboardTitledPane(context: Context) extends VBox:
  spacing = 6
  padding = Insets(6)
  
  val title = new Label()

  val currentValue = new Label {
    alignment = Pos.Center
    text = "0"
  }
  
  val currentAverage = new Label {
    alignment = Pos.Center
    text = "0"
  }

  val controls = List[(String, Label)](
    context.labelCurrent -> currentValue,
    context.labelAverage -> currentAverage
  )
  
  children = List(title, ControlGridPane(controls))