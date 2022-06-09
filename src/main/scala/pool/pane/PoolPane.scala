package pool.pane

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label, SelectionMode, TableColumn, TableView}
import scalafx.scene.layout.{HBox, VBox}

import pool.{Context, Pool}

class PoolPane(context: Context) extends VBox:
  val model = context.model

  val poolLabel = new Label {
    text = context.labelPools
  }

  val poolTableView = new TableView[Pool]() {
    columns ++= List(
      new TableColumn[Pool, String] {
        text = context.tableHeaderName
        cellValueFactory = _.value.nameProperty
      },
      new TableColumn[Pool, String] {
        text = context.tableHeaderBuilt
        cellValueFactory = _.value.builtProperty
      },
      new TableColumn[Pool, String] {
        text = context.tableHeaderVolume
        cellValueFactory = _.value.volumeProperty
      },
      new TableColumn[Pool, String] {
        text = context.tableHeaderUnit
        cellValueFactory = _.value.unitProperty
      }
    )
    model.pools() match
      case Right(pools) => items = pools
      case Left(_) => items = ObservableBuffer[Pool]()
  }
  poolTableView.selectionModel().selectionModeProperty.value = SelectionMode.Single

  val poolAddButton = new Button {
    graphic = context.addImage
  }

  val poolEditButton = new Button {
    graphic = context.editImage
    disable = true
  }

  val poolToolBar = new HBox {
    spacing = 6; children = List(poolAddButton, poolEditButton)
  }

  spacing = 6
  padding = Insets(6)
  children = List(poolLabel, poolTableView, poolToolBar)