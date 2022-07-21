package pool.pane

import scala.concurrent.ExecutionContext.Implicits.global

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, SelectionMode, TableColumn, TableView}
import scalafx.scene.layout.{HBox, Priority, VBox}

import pool.{Cleaning, Context}
import pool.dialog.{CleaningDialog, CleaningsChartDialog}

final class CleaningsPane(context: Context) extends VBox:
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val yesOrNo = (bool: Boolean) => if bool then context.columnYes else context.columnNo

  val tableView = new TableView[Cleaning]():
    columns ++= List(
      new TableColumn[Cleaning, Boolean]:
        text = context.headerBrush
        cellValueFactory = _.value.brushProperty
        cellFactory = (cell, bool) => cell.text = yesOrNo(bool)
      ,
      new TableColumn[Cleaning, Boolean]:
        text = context.headerNet
        cellValueFactory = _.value.netProperty
        cellFactory = (cell, bool) => cell.text = yesOrNo(bool)
      ,
      new TableColumn[Cleaning, Boolean]:
        text = context.headerSkimmerBasket
        cellValueFactory = _.value.skimmerBasketProperty
        cellFactory = (cell, bool) => cell.text = yesOrNo(bool)
      ,
      new TableColumn[Cleaning, Boolean]:
        text = context.headerPumpBasket
        cellValueFactory = _.value.pumpBasketProperty
        cellFactory = (cell, bool) => cell.text = yesOrNo(bool)
      ,
      new TableColumn[Cleaning, Boolean]:
        text = context.headerPumpFilter
        cellValueFactory = _.value.pumpFilterProperty
        cellFactory = (cell, bool) => cell.text = yesOrNo(bool)
      ,
      new TableColumn[Cleaning, Boolean]:
        text = context.headerVacuum
        cellValueFactory = _.value.vacuumProperty
        cellFactory = (cell, bool) => cell.text = yesOrNo(bool)
      ,
      new TableColumn[Cleaning, String]:
        text = context.headerCleaned
        cellValueFactory = _.value.cleanedProperty
    )
    items = model.observableCleanings

  val addButton = new Button:
    graphic = context.addImage
    text = context.buttonAdd
    disable = true
    onAction = { _ => add() }

  val editButton = new Button:
    graphic = context.editImage
    text = context.buttonEdit
    disable = true
    onAction = { _ => update() }

  val chartButton = new Button:
    graphic = context.chartImage
    text = context.buttonChart
    disable = true
    onAction = { _ => chart() }

  val buttonBar = new HBox:
    spacing = 6
    children = List(addButton, editButton, chartButton)

  model.selectedPoolId.onChange { (_, _, _) =>
    addButton.disable = false
    chartButton.disable = false
  }

  children = List(tableView, buttonBar)
  VBox.setVgrow(tableView, Priority.Always)

  tableView.onMouseClicked = { event =>
    if (event.getClickCount == 2 && tableView.selectionModel().getSelectedItem != null) update()
  }

  tableView.selectionModel().selectionModeProperty.value = SelectionMode.Single

  tableView.selectionModel().selectedItemProperty().addListener { (_, _, selectedItem) =>
    // model.update executes a remove and add on items. the remove passes a null selectedItem!
    if selectedItem != null then
      model.selectedCleaningId.value = selectedItem.id
      editButton.disable = false
  }

  def add(): Unit =
    CleaningDialog(context, Cleaning(poolId = model.selectedPoolId.value)).showAndWait() match
      case Some(cleaning: Cleaning) =>
        model
          .add(cleaning)
          .map(cleaning => tableView.selectionModel().select(cleaning))
          .recover { case error: Throwable => model.onError(error, "Cleaning add failed.") }
      case _ =>

  def update(): Unit =
    val selectedIndex = tableView.selectionModel().getSelectedIndex
    val cleaning = tableView.selectionModel().getSelectedItem.cleaning
    CleaningDialog(context, cleaning).showAndWait() match
      case Some(cleaning: Cleaning) =>
        model
          .update(selectedIndex, cleaning)
          .map(_ => tableView.selectionModel().select(selectedIndex))
          .recover { case error: Throwable => model.onError(error, "Cleaning update failed.") }
      case _ =>

  def chart(): Unit = CleaningsChartDialog(context).showAndWait()