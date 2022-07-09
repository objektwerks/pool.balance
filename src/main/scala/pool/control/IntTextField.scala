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
      change // only if change is a number
    else
      change.setText("") // else make no change
      // change.setRange(change.getRangeStart(), change.getRangeStart()) // don't remove any selected text
      change
  }
  val formatter = new TextFormatter[Int](converter, 0, filter)

  textFormatter = formatter