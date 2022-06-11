package pool.pane

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label, SelectionMode, TableColumn, TableView}
import scalafx.scene.layout.{HBox, VBox}

import pool.{Chemical, Context, Pool}

class ChemicalsPane(context: Context) extends VBox with AddEditToolbar(context):
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
      new TableColumn[Chemical, String] {
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

  children = List(label, tableView, toolbar)

  tableView.selectionModel().selectionModeProperty.value = SelectionMode.Single
