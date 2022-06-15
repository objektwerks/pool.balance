package pool.control

import java.text.{DecimalFormat, NumberFormat}

import math.BigDecimal.double2bigDecimal

import scalafx.scene.control.{Label, Slider, TextField, TextFormatter}
import scalafx.scene.layout.HBox
import scalafx.util.converter.FormatStringConverter

import pool.{Context, Measurement}

class MeasurementSlider(labelText: String, 
                        textFieldText: String,
                        formatConverter: FormatStringConverter[Number]) extends HBox:
  import MeasurementSliders.*

  val label = new Label {
    text = labelText
  }
  val slider = new Slider {
    prefWidth = 600
    showTickLabels = true
    showTickMarks = true
  }
  val textField = new TextField {
    text = textFieldText
    textFormatter = new TextFormatter[Number](formatConverter) {
      value <==> slider.value
    }
  }
  new HBox {
    spacing = 3
    children = List(label, textField, slider)
  }

/**
  * free chlorine (fc): 0 - 10, ok = 1 - 5, ideal = 3
  * combined chlorine (cc = tc - fc): 0 - 0.5, ok = 0.2, ideal = 0
  * total chlorine (tc = fc + cc): 0 - 10, ok = 1 - 5, ideal = 3
  * ph: 6.2 - 8.4, ok = 7.2 - 7.6, ideal = 7.4
  * calcium hardness (ch): 0 - 1000, ok = 250 - 500, ideal = 375
  * total alkalinity (ta): 0 - 240, ok = 80 - 120, ideal = 100
  * cyanuric acid (cya): 0 - 300, ok = 30 - 100, ideal = 50
  * total bromine (tb): 0 - 20, ok = 2 - 10, ideal = 5
  * temperature: 50 - 110
 */
object MeasurementSliders:
  def decimalFormat: DecimalFormat = DecimalFormat("####.#")
  def integerFormat: NumberFormat = NumberFormat.getIntegerInstance

  def formatConverter(format: DecimalFormat): FormatStringConverter[Number] = new FormatStringConverter[Number](format)
  def formatConverter(format: NumberFormat): FormatStringConverter[Number] = new FormatStringConverter[Number](format)

  def freeChlorineSlider(context: Context,
                         measurement: Measurement): HBox =
    new MeasurementSlider(labelText = context.labelFreeChlorine,
                          textFieldText = measurement.freeChlorine.toString,
                          formatConverter = formatConverter(integerFormat)) {
                            slider.min = 0
                            slider.max = 10
                            slider.majorTickUnit = 1
                            slider.value = measurement.freeChlorine
                          }