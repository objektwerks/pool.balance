package pool.pane

import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.VBox
import scalafx.scene.control.{Label, TableColumn, TableView}

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