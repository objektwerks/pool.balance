package pool.dialog

import scalafx.Includes.*
import scalafx.scene.control.{ButtonType, Dialog}
import scalafx.scene.layout.VBox

import pool.{App, Context}

class ConverterDialog(context: Context) extends Dialog[Unit]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogConverters

  dialogPane().buttonTypes = List(ButtonType.Close)
  dialogPane().content = new VBox {
    prefWidth = 600
    prefHeight = 200
    spacing = 6
    children = List()
  }