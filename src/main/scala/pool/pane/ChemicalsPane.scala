package pool.pane

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, SelectionMode, TableColumn, TableView}
import scalafx.scene.layout.{HBox, Priority, VBox}

import pool.{Chemical, Context, Pool}
import pool.dialog.ChemicalDialog

class ChemicalsPane(context: Context) extends VBox with AddEditButtonBar(context):
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val tableView = new TableView[Chemical]() {
    columns ++= List(
      new TableColumn[Chemical, String] {
        text = context.tableTypeof
        cellValueFactory = _.value.typeofProperty
      },
      new TableColumn[Chemical, Double] {
        text = context.tableAmount
        cellValueFactory = _.value.amountProperty
      },
      new TableColumn[Chemical, String] {
        text = context.tableUnit
        cellValueFactory = _.value.unitProperty
      },
      new TableColumn[Chemical, String] {
        text = context.tableAdded
        cellValueFactory = _.value.addedProperty
      }
    )
    items = model.observableChemicals
  }

  model.selectedPoolId.onChange { (_, _, _) =>
    addButton.disable = false
  }

  children = List(tableView, addEditButtonBar)
  VBox.setVgrow(tableView, Priority.Always)

  tableView.selectionModel().selectionModeProperty.value = SelectionMode.Single

  tableView.selectionModel().selectedItemProperty().addListener { (_, _, selectedItem) =>
    // model.update executes a remove and add on items. the remove passes a null selectedItem!
    if selectedItem != null then
      model.selectedChemicalId.value = selectedItem.id
      editButton.disable = false
  }

  def add(): Unit =
    ChemicalDialog(context, Chemical(poolId = model.selectedPoolId.value)).showAndWait() match
      case Some(chemical: Chemical) => model.add(chemical).fold(_ => (), chemical => tableView.selectionModel().select(chemical))
      case _ => model.onError("Chemical Dialog add failed.")

  def update(): Unit =
    val selectedIndex = tableView.selectionModel().getSelectedIndex
    val chemical = tableView.selectionModel().getSelectedItem.chemical
    ChemicalDialog(context, chemical).showAndWait() match
      case Some(chemical: Chemical) =>
        model.update(selectedIndex, chemical)
        tableView.selectionModel().select(selectedIndex)
      case _ => model.onError("Chemical Dialog update failed.")