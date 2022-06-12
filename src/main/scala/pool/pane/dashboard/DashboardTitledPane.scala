package pool.pane.dashboard

import scalafx.geometry.Pos
import scalafx.scene.control.{Label, TitledPane}

import pool.Context
import pool.dialog.ControlGridPane

abstract class DashboardTitledPane(context: Context) extends TitledPane:
  collapsible = false

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
  
  content = ControlGridPane(controls)