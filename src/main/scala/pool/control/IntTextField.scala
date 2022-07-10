package pool.control

import scalafx.scene.control.{TextField, TextFormatter}
import scalafx.scene.control.TextFormatter.Change
import scalafx.util.converter.IntStringConverter

object IntTextField:
  val regex = "\\d+"

class IntTextField extends TextField:
  val converter = IntStringConverter()
  val filter: (Change) => Change = { (change: Change) =>
    if change.text.matches(IntTextField.regex) then
      change // only if change is an integer
    else
      change.setText("") // else make no change
      change
  }
  val formatter = new TextFormatter[Int](converter, 0, filter)

  textFormatter = formatter