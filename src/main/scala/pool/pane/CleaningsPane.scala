package pool.pane

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label, SelectionMode, TableColumn, TableView}
import scalafx.scene.layout.{HBox, VBox}

import pool.{Cleaning, Context}
import pool.dialog.CleaningDialog

class CleaningsPane(context: Context) extends VBox with AddEditButtonBar(context):
  spacing = 6
  padding = Insets(6)
  maxWidth = Double.MaxValue
  maxHeight = Double.MaxValue

  val model = context.model

  val label = new Label {
    text = context.labelCleanings
  }

  val tableView = new TableView[Cleaning]() {
    columns ++= List(
      new TableColumn[Cleaning, Boolean] {
        text = context.tableBrush
        cellValueFactory = _.value.brushProperty
      },
      new TableColumn[Cleaning, Boolean] {
        text = context.tableNet
        cellValueFactory = _.value.netProperty
      },
      new TableColumn[Cleaning, Boolean] {
        text = context.tableSkimmerBasket
        cellValueFactory = _.value.skimmerBasketProperty
      },
      new TableColumn[Cleaning, Boolean] {
        text = context.tablePumpBasket
        cellValueFactory = _.value.pumpBasketProperty
      },
      new TableColumn[Cleaning, Boolean] {
        text = context.tablePumpFilter
        cellValueFactory = _.value.pumpFilterProperty
      },
      new TableColumn[Cleaning, Boolean] {
        text = context.tableVacuum
        cellValueFactory = _.value.vacuumProperty
      },
      new TableColumn[Cleaning, String] {
        text = context.tableCleaned
        cellValueFactory = _.value.cleanedProperty
      }
    )
    items = model.cleanings(model.selectedPoolId.value).fold( _ => ObservableBuffer[Cleaning](), cleanings => cleanings)
  }

  children = List(label, tableView, addEditButtonBar)

  tableView.selectionModel().selectionModeProperty.value = SelectionMode.Single

  tableView.selectionModel().selectedItemProperty().addListener { (_, _, selectedItem) =>
    // model.update executes a remove and add on items. the remove passes a null selectedItem!
    if selectedItem != null then
      model.selectedCleaningId.value = selectedItem.id
      editButton.disable = false
  }

  def add(): Unit =
    CleaningDialog(context, Cleaning(poolId = model.selectedPoolId.value)).showAndWait() match
      case Some(cleaning: Cleaning) => model.add(cleaning).fold(_ => (), cleaning => tableView.selectionModel().select(cleaning))
      case _ =>

  def update(): Unit =
    val selectedIndex = tableView.selectionModel().getSelectedIndex
    val cleaning = tableView.selectionModel().getSelectedItem.cleaning
    CleaningDialog(context, cleaning).showAndWait() match
      case Some(cleaning: Cleaning) =>
        model.update(selectedIndex, cleaning)
        tableView.selectionModel().select(selectedIndex)
      case _ =>