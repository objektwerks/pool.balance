package pool.pane

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, SelectionMode, TableColumn, TableView}
import scalafx.scene.layout.{HBox, Priority, VBox}

import pool.{Measurement, Context}
import pool.dialog.{MeasurementDialog, MeasurementsChartDialog}

class MeasurementsPane(context: Context) extends VBox with PaneButtonBar(context):
  spacing = 6
  padding = Insets(6)

  val model = context.model

  val tableView = new TableView[Measurement]() {
    columns ++= List(
      new TableColumn[Measurement, Int] {
        text = context.headerTotalChlorine
        cellValueFactory = _.value.totalChlorineProperty
      },
      new TableColumn[Measurement, Int] {
        text = context.headerFreeChlorine
        cellValueFactory = _.value.freeChlorineProperty
      },
      new TableColumn[Measurement, Double] {
        text = context.headerCombinedChlorine
        cellValueFactory = _.value.combinedChlorineProperty
      },
      new TableColumn[Measurement, Double] {
        prefWidth = 50
        text = context.headerPh
        cellValueFactory = _.value.phProperty
      },
      new TableColumn[Measurement, Int] {
        text = context.headerCalciumHardness
        cellValueFactory = _.value.calciumHardnessProperty
      },
      new TableColumn[Measurement, Int] {
        text = context.headerTotalAlkalinity
        cellValueFactory = _.value.totalAlkalinityProperty
      },
      new TableColumn[Measurement, Int] {
        text = context.headerCyanuricAcid
        cellValueFactory = _.value.cyanuricAcidProperty
      },
      new TableColumn[Measurement, Int] {
        text = context.headerTotalBromine
        cellValueFactory = _.value.totalBromineProperty
      },
      new TableColumn[Measurement, Int] {
        prefWidth = 60
        text = context.headerSalt
        cellValueFactory = _.value.saltProperty
      },
      new TableColumn[Measurement, Int] {
        prefWidth = 50
        text = context.headerTemperature
        cellValueFactory = _.value.temperatureProperty
      },
      new TableColumn[Measurement, String] {
        text = context.headerMeasured
        cellValueFactory = _.value.measuredProperty
      }
    )
    items = model.observableMeasurements
  }

  model.selectedPoolId.onChange { (_, _, _) =>
    addButton.disable = false
    chartButton.disable = false
  }

  addChartButton()
  children = List(tableView, paneButtonBar)
  VBox.setVgrow(tableView, Priority.Always)

  tableView.selectionModel().selectionModeProperty.value = SelectionMode.Single

  tableView.selectionModel().selectedItemProperty().addListener { (_, _, selectedItem) =>
    // model.update executes a remove and add on items. the remove passes a null selectedItem!
    if selectedItem != null then
      model.selectedMeasurementId.value = selectedItem.id
      editButton.disable = false
  }

  def add(): Unit =
    MeasurementDialog(context, Measurement(poolId = model.selectedPoolId.value)).showAndWait() match
      case Some(measurement: Measurement) => model.add(measurement).fold(_ => (), measurement => tableView.selectionModel().select(measurement))
      case _ => model.onError("Measurement add failed.")

  def update(): Unit =
    val selectedIndex = tableView.selectionModel().getSelectedIndex
    val measurement = tableView.selectionModel().getSelectedItem.measurement
    MeasurementDialog(context, measurement).showAndWait() match
      case Some(measurement: Measurement) =>
        model.update(selectedIndex, measurement)
        tableView.selectionModel().select(selectedIndex)
      case _ => model.onError("Measurement update failed.")

  override def chart(): Unit =
    MeasurementsChartDialog(context).showAndWait() match
      case Some(_) =>
      case _ => model.onError("Measurements chart failed.")