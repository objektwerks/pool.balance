package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.control.{Tab, TabPane}

import pool.Context

class TabbedPane(context: Context) extends TabPane:
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

  padding = Insets(6)
  tabs = Seq(cleaningsTab, measurementsTab, chemicalsTab)