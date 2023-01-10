package pool.dialog

import scalafx.Includes.*
import scalafx.scene.control.{ButtonType, Dialog, TableColumn, TableView}
import scalafx.scene.layout.VBox

import pool.{App, Error, Context}

final class ErrorsDialog(context: Context) extends Dialog[Unit]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogErrors

  val model = context.model

  val tableView = new TableView[Error]():
    columns ++= List(
      new TableColumn[Error, String]:
        text = context.headerOccurred
        cellValueFactory = _.value.occurredProperty
      ,
      new TableColumn[Error, String]:
        prefWidth = 425
        text = context.headerError
        cellValueFactory = _.value.messageProperty
    )
    items = model.observableErrors

  dialogPane().buttonTypes = List(ButtonType.Close)
  dialogPane().content = new VBox:
    prefWidth = 600
    prefHeight = 200
    spacing = 6
    children = List(tableView)