package pool.control

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout.GridPane

class Converter extends GridPane:
  hgap = 6
  vgap = 6
  padding = Insets(top = 6, right = 6, bottom = 6, left = 6)

  val glTextField = new DoubleTextField:
    text = "0.0"

  val glLabel = new Label:
    alignment = Pos.Center
    text = "gallons - gl"

  val lTextField = new DoubleTextField:
    text = "0.0"

  val lLabel = new Label:
    alignment = Pos.Center
    text = "liters - l"

  val lbTextField = new DoubleTextField:
    text = "0.0"

  val lbLabel = new Label:
    alignment = Pos.Center
    text = "pounds - lb"

  val kgTextField = new DoubleTextField:
    text = "0.0"

  val kgLabel = new Label:
    alignment = Pos.Center
    text = "kilograms - kg"
