package pool.dashboard

import scalafx.geometry.Pos
import scalafx.scene.control.{Label, TitledPane}

import pool.Context
import pool.dialog.ControlGridPane

abstract class DashboardTitledPane(context: Context) extends TitledPane:
  collapsible = false
  maxWidth = Double.MaxValue
  maxHeight = Double.MaxValue

  val range = new Label {
    alignment = Pos.Center
    text = "(0 - 0)"
  }

  val ideal = new Label {
    alignment = Pos.Center
    text = "0"
  }
  
  val current = new Label {
    alignment = Pos.Center
    text = "0"
  }
  
  val average = new Label {
    alignment = Pos.Center
    text = "0"
  }

  val controls = List[(String, Label)](
    context.labelRange -> range,
    context.labelIdeal -> ideal,
    context.labelCurrent -> current,
    context.labelAverage -> average
  )
  
  content = ControlGridPane(controls)