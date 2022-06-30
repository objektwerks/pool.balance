package pool.pane

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, SelectionMode, TableColumn, TableView}
import scalafx.scene.layout.{HBox, Priority, VBox}

import pool.{Cleaning, Context}
import pool.dialog.{CleaningDialog, CleaningsChartDialog}

class CleaningsPane(context: Context) extends VBox with PaneButtonBar(context):
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val tableView = new TableView[Cleaning]() {
    columns ++= List(
      new TableColumn[Cleaning, Boolean] {
        text = context.headerBrush
        cellValueFactory = _.value.brushProperty
      },
      new TableColumn[Cleaning, Boolean] {
        text = context.headerNet
        cellValueFactory = _.value.netProperty
      },
      new TableColumn[Cleaning, Boolean] {
        text = context.headerSkimmerBasket
        cellValueFactory = _.value.skimmerBasketProperty
      },
      new TableColumn[Cleaning, Boolean] {
        text = context.headerPumpBasket
        cellValueFactory = _.value.pumpBasketProperty
      },
      new TableColumn[Cleaning, Boolean] {
        text = context.headerPumpFilter
        cellValueFactory = _.value.pumpFilterProperty
      },
      new TableColumn[Cleaning, Boolean] {
        text = context.headerVacuum
        cellValueFactory = _.value.vacuumProperty
      },
      new TableColumn[Cleaning, String] {
        text = context.headerCleaned
        cellValueFactory = _.value.cleanedProperty
      }
    )
    items = model.observableCleanings
  }

  model.selectedPoolId.onChange { (_, _, _) =>
    addButton.disable = false
    chartButton.disable = false
  }

  children = List(tableView, addEditChartButtonBar)
  VBox.setVgrow(tableView, Priority.Always)

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
      case _ => model.onError("Cleaning add failed.")

  def update(): Unit =
    val selectedIndex = tableView.selectionModel().getSelectedIndex
    val cleaning = tableView.selectionModel().getSelectedItem.cleaning
    CleaningDialog(context, cleaning).showAndWait() match
      case Some(cleaning: Cleaning) =>
        model.update(selectedIndex, cleaning)
        tableView.selectionModel().select(selectedIndex)
      case _ => model.onError("Cleaning update failed.")

  override def chart(): Unit =
    CleaningsChartDialog(context).showAndWait() match
      case Some(_) =>
      case _ => model.onError("Cleanings chart failed.")