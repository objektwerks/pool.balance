package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.control.{TextField, TitledPane}

import pool.Context
import pool.dialog.ControlGridPane

abstract class DashboardTitledPane(context: Context) extends TitledPane:
  collapsible = false
  padding = Insets(6)

  val currentValue = new TextField {
    editable = false
    text = "0"
  }
  
  val currentAverage = new TextField {
    editable = false
    text = "0"
  }

  val controls = List[(String, TextField)](
    context.labelCurrent -> currentValue,
    context.labelAverage -> currentAverage
  )
  
  content = ControlGridPane(controls)