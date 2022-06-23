package pool.pane

import scalafx.scene.control.Button
import scalafx.scene.layout.{HBox, VBox}

import pool.Context

trait PaneButtonBar(val context: Context):
  val addButton = new Button {
    graphic = context.addImage
    text = context.buttonAdd
    disable = true
    onAction = { _ => add() }
  }

  val editButton = new Button {
    graphic = context.editImage
    text = context.buttonEdit
    disable = true
    onAction = { _ => update() }
  }

  val chartButton = new Button {
    graphic = context.chartImage
    text = context.buttonChart
    disable = true
    onAction = { _ => chart() }
  }

  val paneButtonBar = new HBox {
    spacing = 6
    children = List(addButton, editButton)
  }

  def addChartButton(): Unit = paneButtonBar.children += chartButton

  def add(): Unit

  def update(): Unit

  def chart(): Unit = ()