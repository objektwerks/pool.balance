package pool.control

import scalafx.scene.control.{TextField, TextFormatter}
import scalafx.scene.control.TextFormatter.Change
import scalafx.util.converter.DoubleStringConverter

object DoubleTextField:
  val regex = "\\d+\\.\\d+"

class DoubleTextField extends TextField:
  val converter = DoubleStringConverter()
  val filter: (Change) => Change = { (change: Change) =>
    if change.text.matches(DoubleTextField.regex) then
      change // only if change is a number
    else
      change.setText("") // else make no change
      // change.setRange(change.getRangeStart(), change.getRangeStart()) // don't remove any selected text
      change
  }
  val formatter = new TextFormatter[Double](converter, 0.0, filter)

  textFormatter = formatter