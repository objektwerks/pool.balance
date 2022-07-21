package pool.dialog

import scalafx.Includes.*
import scalafx.scene.control.{ButtonType, Dialog}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.layout.VBox

import pool.{App, Context}
import pool.chart.MeasurementsChart

final class MeasurementsChartDialog(context: Context) extends Dialog[Unit]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.chartMeasurements
  
  dialogPane().buttonTypes = List(ButtonType.Close)
  dialogPane().content = new VBox:
    spacing = 6
    children = List( MeasurementsChart(context) )