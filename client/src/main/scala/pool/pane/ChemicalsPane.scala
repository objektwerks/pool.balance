package pool.pane

import scala.concurrent.ExecutionContext.Implicits.global

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, SelectionMode, TableColumn, TableView}
import scalafx.scene.layout.{HBox, Priority, VBox}

import pool.{Chemical, Context, Pool}
import pool.dialog.{ChemicalDialog, ChemicalsChartDialog}

final class ChemicalsPane(context: Context) extends VBox:
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val tableView = new TableView[Chemical]():
    columns ++= List(
      new TableColumn[Chemical, String]:
        prefWidth = 150
        text = context.headerTypeof
        cellValueFactory = _.value.typeofProperty
      ,
      new TableColumn[Chemical, Double]:
        text = context.headerAmount
        cellValueFactory = _.value.amountProperty
      ,
      new TableColumn[Chemical, String]:
        text = context.headerUnit
        cellValueFactory = _.value.unitProperty
      ,
      new TableColumn[Chemical, String]:
        text = context.headerAdded
        cellValueFactory = _.value.addedProperty
    )
    items = model.observableChemicals

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
      model.selectedChemicalId.value = selectedItem.id
      editButton.disable = false
  }

  def add(): Unit =
    ChemicalDialog(context, Chemical(poolId = model.selectedPoolId.value)).showAndWait() match
      case Some(chemical: Chemical) =>
        model
          .add(chemical)
          .map(chemical => tableView.selectionModel().select(chemical))
          .recover { case error: Throwable => model.onError(error, "Chemical add failed.") }
      case _ =>

  def update(): Unit =
    val selectedIndex = tableView.selectionModel().getSelectedIndex
    val chemical = tableView.selectionModel().getSelectedItem.chemical
    ChemicalDialog(context, chemical).showAndWait() match
      case Some(chemical: Chemical) =>
        model
          .update(selectedIndex, chemical)
          .map(_ => tableView.selectionModel().select(selectedIndex))
          .recover { case error: Throwable => model.onError(error, "Chemical update failed.") }
      case _ =>

  def chart(): Unit = ChemicalsChartDialog(context).showAndWait()