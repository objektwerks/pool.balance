package pool.pane

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label, SelectionMode, TableColumn, TableView}
import scalafx.scene.layout.{HBox, VBox}

import pool.{Cleaning, Context}

class CleaningsPane(context: Context) extends VBox with AddEditToolbar(context):
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val label = new Label {
    text = context.labelCleanings
  }

  val tableView = new TableView[Cleaning]() {
    columns ++= List(
      new TableColumn[Cleaning, String] {
        text = context.tableBrush
        cellValueFactory = _.value.brushProperty
      },
      new TableColumn[Cleaning, String] {
        text = context.tableNet
        cellValueFactory = _.value.netProperty
      },
      new TableColumn[Cleaning, String] {
        text = context.tableSkimmerBasket
        cellValueFactory = _.value.skimmerBasketProperty
      },
      new TableColumn[Cleaning, String] {
        text = context.tablePumpBasket
        cellValueFactory = _.value.pumpBasketProperty
      },
      new TableColumn[Cleaning, String] {
        text = context.tablePumpFilter
        cellValueFactory = _.value.pumpFilterProperty
      },
      new TableColumn[Cleaning, String] {
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

  children = List(label, tableView, toolbar)

  tableView.selectionModel().selectionModeProperty.value = SelectionMode.Single

