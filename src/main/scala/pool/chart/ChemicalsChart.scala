package pool.chart

import scalafx.geometry.Insets
import scalafx.scene.control.TabPane

class ChemicalsChart extends TabPane with Chart:
  padding = Insets(6)
  tabs = List()