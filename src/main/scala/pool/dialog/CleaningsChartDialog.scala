package pool.dialog

import scalafx.Includes.*
import scalafx.scene.control.{ButtonType, Dialog}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.layout.VBox

import pool.{App, Context}
import pool.chart.CleaningsChart

final class CleaningsChartDialog(context: Context) extends Dialog[Unit]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.chartCleanings
  
  dialogPane().buttonTypes = List(ButtonType.Close)
  dialogPane().content = new VBox:
    spacing = 6
    children = List( CleaningsChart(context) )