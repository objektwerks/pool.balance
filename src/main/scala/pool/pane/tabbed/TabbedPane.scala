package pool.pane.tabbed

import scalafx.geometry.Insets
import scalafx.scene.control.{Tab, TabPane}

import pool.Context

class TabbedPane(context: Context) extends TabPane:
  val cleaningsTab = new Tab {
    text = context.tabCleanings
    closable = false
    content = CleaningsPane(context)
  }

  val measurementsTab = new Tab {
  	text = context.tabMeasurements
  	closable = false
  	content = MeasurementsPane(context)
  }

  val chemicalsTab = new Tab {
  	text = context.tabChemicals
  	closable = false
  	content = ChemicalsPane(context)
  }

  padding = Insets(6)
  tabs = Seq(cleaningsTab, measurementsTab, chemicalsTab)