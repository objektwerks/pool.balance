package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.control.{Tab, TabPane}
import scalafx.scene.layout.{Priority, VBox}

import pool.Context

final class TabbedPane(context: Context) extends VBox:
  padding = Insets(6)

  val cleaningsTab = new Tab:
    text = context.tabCleanings
    closable = false
    content = CleaningsPane(context)

  val measurementsTab = new Tab:
  	text = context.tabMeasurements
  	closable = false
  	content = MeasurementsPane(context)

  val chemicalsTab = new Tab:
  	text = context.tabChemicals
  	closable = false
  	content = ChemicalsPane(context)

  val tabPane = new TabPane:
    tabs = List(cleaningsTab, measurementsTab, chemicalsTab)

  children = List(tabPane)
  VBox.setVgrow(tabPane, Priority.Always)