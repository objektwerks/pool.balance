package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.control.{TableColumn, TableView}
import scalafx.scene.layout.VBox

import pool.Context
import scalafx.beans.value.ObservableValue

class ErrorsPane(context: Context) extends VBox:
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val tableView = new TableView[Error]() {
    columns ++= List(
      new TableColumn[Error, String] {
        prefWidth = 150
        text = context.headerAdded
        cellValueFactory = _.value.
      },
      new TableColumn[Error, String] {
        text = context.headerAdded
        cellValueFactory = _.value.
      }
    )
    items = model.observableErrors
  }

  children = List(tableView)