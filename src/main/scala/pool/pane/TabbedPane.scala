package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.layout.VBox
import scalafx.scene.control.{Tab, TabPane}

import pool.Context

class TabbedPane(context: Context) extends VBox:
  spacing = 6
  padding = Insets(6)

  val cleaningsTab = new Tab {
    text = context.tabCleanings
    closable = false
    content = CleaningPane(context)
  }

  val measurementsTab = new Tab {
  	text = context.tabMeasurements
  	closable = false
  	content = MeasurementPane(context)
  }

  val chemicalsTab = new Tab {
  	text = context.tabChemicals
  	closable = false
  	content = ChemicalPane(context)
  }

  val tabPane = new TabPane {
    padding = Insets(6)
    tabs = Seq(cleaningsTab, measurementsTab, chemicalsTab)    
  }

  children = List(tabPane)