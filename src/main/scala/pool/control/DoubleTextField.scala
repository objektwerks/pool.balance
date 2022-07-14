package pool.control

import scalafx.scene.control.{TextField, TextFormatter}
import scalafx.scene.control.TextFormatter.Change
import scalafx.util.converter.DoubleStringConverter

object DoubleTextField:
  val regex = """([0-9][.])?[0-9]+""".r

class DoubleTextField extends TextField:
  val converter = DoubleStringConverter()
  val filter: Change => Change = { (change: Change) =>
    if DoubleTextField.regex.matches(change.text) then
      println(s"double match: ${change.text}")
      change // if double, make change
    else
      if !change.controlText.contains(".") && change.text.contains(".") then
        println(s"'.' match: ${change.text}")
        change // if only . then make change
      else 
        println(s"'.' already in double: ${change.text}")
        change.setText("") // else make no change
      change
  }
  val formatter = new TextFormatter[Double](converter, 0.0, filter)

  textFormatter = formatter