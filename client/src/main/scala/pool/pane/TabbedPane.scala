package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.control.{Tab, TabPane}
import scalafx.scene.layout.{Priority, VBox}

import pool.{Context, Model}

final class TabbedPane(context: Context, model: Model) extends VBox:
  padding = Insets(6)

  val cleaningsTab = new Tab:
    text = context.tabCleanings
    closable = false
    content = CleaningsPane(context, model)

  val measurementsTab = new Tab:
  	text = context.tabMeasurements
  	closable = false
  	content = MeasurementsPane(context, model)

  val chemicalsTab = new Tab:
  	text = context.tabChemicals
  	closable = false
  	content = ChemicalsPane(context, model)

  val tabPane = new TabPane:
    tabs = List(cleaningsTab, measurementsTab, chemicalsTab)

  children = List(tabPane)
  VBox.setVgrow(tabPane, Priority.Always)