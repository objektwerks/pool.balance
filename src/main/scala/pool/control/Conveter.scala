package pool.control

import scalafx.geometry.Insets
import scalafx.scene.layout.GridPane

class Conveter extends GridPane:
  hgap = 6
  vgap = 6
  padding = Insets(top = 6, right = 6, bottom = 6, left = 6)

  val glTextField = new DoubleTextField {
    text = "0.0"
  }

  val lTextField = new DoubleTextField {
    text = "0.0"
  }

  val lbTextField = new DoubleTextField {
    text = "0.0"
  }

  val kgTextField = new DoubleTextField {
    text = "0.0"
  }