package pool.dialog

import scalafx.Includes.*
import scalafx.scene.control.{ButtonType, Dialog}
import scalafx.scene.layout.VBox

import pool.{Client, Context, Model}
import pool.chart.MeasurementsChart

final class MeasurementsChartDialog(context: Context, model: Model) extends Dialog[Unit]:
  initOwner(Client.stage)
  title = context.windowTitle
  headerText = context.chartMeasurements
  
  dialogPane().buttonTypes = List(ButtonType.Close)
  dialogPane().content = new VBox:
    spacing = 6
    children = List( MeasurementsChart(context, model) )