package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.control.{TextField, TitledPane}

import pool.Context
import pool.dialog.ControlGridPane

class FreeChlorinePane(context: Context) extends TitledPane:
  collapsible = false
  padding = Insets(6)
  text = context.tableFreeChlorine

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