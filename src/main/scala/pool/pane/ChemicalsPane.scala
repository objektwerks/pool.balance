package pool.pane

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label, SelectionMode, TableColumn, TableView}
import scalafx.scene.layout.{HBox, VBox}

import pool.{Chemical, Context, Pool}
import pool.dialog.ChemicalDialog

class ChemicalsPane(context: Context) extends VBox with AddEditButtonBar(context):
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val label = new Label {
    text = context.labelChemicals
  }

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
    items = model.chemicals(model.selectedPoolId.value).fold( _ => ObservableBuffer[Chemical](), chemicals => chemicals)
  }

  children = List(label, tableView, addEditButtonBar)

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
      case _ =>

  def update(): Unit =
    val selectedIndex = tableView.selectionModel().getSelectedIndex
    val chemical = tableView.selectionModel().getSelectedItem.chemical
    ChemicalDialog(context, chemical).showAndWait() match
      case Some(chemical: Chemical) =>
        model.update(selectedIndex, chemical)
        tableView.selectionModel().select(selectedIndex)
      case _ =>