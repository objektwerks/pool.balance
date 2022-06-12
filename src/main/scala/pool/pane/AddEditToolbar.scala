package pool.pane

import scalafx.scene.control.Button
import scalafx.scene.layout.{HBox, VBox}

import pool.Context

trait AddEditToolbar(val context: Context):
  val addButton = new Button {
    text = context.paneAdd
    graphic = context.addImage
  }

  val editButton = new Button {
    text = context.paneEdit
    graphic = context.editImage
    disable = true
  }

  val toolbar = new HBox {
    spacing = 6
    children = List(addButton, editButton)
  }