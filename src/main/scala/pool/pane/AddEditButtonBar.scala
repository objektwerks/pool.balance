package pool.pane

import scalafx.scene.control.Button
import scalafx.scene.layout.{HBox, VBox}

import pool.Context

trait AddEditButtonBar(val context: Context):
  val addButton = new Button {
    graphic = context.addImage
    text = context.paneAdd
    onAction = { _ => add() }
  }

  val editButton = new Button {
    graphic = context.editImage
    text = context.paneEdit
    disable = true
    onAction = { _ => update() }
  }

  val addEditButtonBar = new HBox {
    spacing = 6
    children = List(addButton, editButton)
  }

  def add(): Unit

  def update(): Unit