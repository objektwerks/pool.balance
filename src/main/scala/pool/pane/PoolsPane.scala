package pool.pane

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, SelectionMode, Tab, TabPane, TableColumn, TableView}
import scalafx.scene.layout.{Priority, VBox}

import pool.{Context, Pool, UnitOfMeasure}
import pool.dialog.PoolDialog

class PoolsPane(context: Context) extends VBox with PaneButtonBar(context):
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val tableView = new TableView[Pool]() {
    columns ++= List(
      new TableColumn[Pool, String] {
        text = context.headerName
        cellValueFactory = _.value.nameProperty
      },
      new TableColumn[Pool, Int] {
        text = context.headerVolume
        cellValueFactory = _.value.volumeProperty
      },
      new TableColumn[Pool, String] {
        text = context.headerUnit
        cellValueFactory = _.value.unitProperty
      }
    )
    items = model.observablePools
  }

  addButton.disable = false
  
  val tab = new Tab {
  	text = context.labelPools
  	closable = false
  	content = new VBox {
      spacing = 6
      padding = Insets(6)
      children = List(tableView, addEditButtonBar)
    }
  }

  val tabPane = new TabPane {
    tabs = Seq(tab)
  }

  children = List(tabPane)
  VBox.setVgrow(tableView, Priority.Always)
  VBox.setVgrow(tabPane, Priority.Always)

  tableView.selectionModel().selectionModeProperty.value = SelectionMode.Single
  
  tableView.selectionModel().selectedItemProperty().addListener { (_, _, selectedItem) =>
    // model.update executes a remove and add on items. the remove passes a null selectedItem!
    if selectedItem != null then
      model.selectedPoolId.value = selectedItem.id
      editButton.disable = false
  }

  def add(): Unit =
    PoolDialog(context, Pool()).showAndWait() match
      case Some(pool: Pool) => model.add(pool).fold(_ => (), pool => tableView.selectionModel().select(pool))
      case _ => model.onError("Pool add failed.")

  def update(): Unit =
    val selectedIndex = tableView.selectionModel().getSelectedIndex
    val pool = tableView.selectionModel().getSelectedItem.pool
    PoolDialog(context, pool).showAndWait() match
      case Some(pool: Pool) =>
        model.update(selectedIndex, pool)
        tableView.selectionModel().select(selectedIndex)
      case _ => model.onError("Pool update failed.")