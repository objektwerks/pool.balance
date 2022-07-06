package pool.pane

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{TableColumn, TableView}
import scalafx.scene.layout.VBox

import pool.{Error, Context}

class ErrorsPane(context: Context) extends VBox:
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val tableView = new TableView[Error]() {
    columns ++= List(
      new TableColumn[Error, String] {
        prefWidth = 150
        text = context.headerAdded
        cellValueFactory = _.value.messageProperty
      },
      new TableColumn[Error, String] {
        text = context.headerAdded
        cellValueFactory = _.value.occurredProperty
      }
    )
    items = model.observableErrors
  }

  children = List(tableView)