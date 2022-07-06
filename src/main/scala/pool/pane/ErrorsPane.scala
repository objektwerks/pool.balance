package pool.pane

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Tab, TabPane, TableColumn, TableView}
import scalafx.scene.layout.VBox

import pool.{Error, Context}

class ErrorsPane(context: Context) extends VBox:
  prefHeight = 50
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val tableView = new TableView[Error]() {
    columns ++= List(
      new TableColumn[Error, String] {
        prefWidth = 150
        text = context.headerOccurred
        cellValueFactory = _.value.messageProperty
      },
      new TableColumn[Error, String] {
        text = context.headerError
        cellValueFactory = _.value.occurredProperty
      }
    )
    items = model.observableErrors
  }

  val tab = new Tab {
  	text = context.labelPools
  	closable = false
  	content = new VBox {
      spacing = 6
      padding = Insets(6)
      children = List(tableView)
    }
  }

  val tabPane = new TabPane {
    tabs = List(tab)
  }

  children = List(tabPane)