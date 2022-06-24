package pool.chart

import scalafx.geometry.Insets
import scalafx.scene.control.TabPane

class MeasurementsChart extends TabPane with Chart:
  padding = Insets(6)
  tabs = List()