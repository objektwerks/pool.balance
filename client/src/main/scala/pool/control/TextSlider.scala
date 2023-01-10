package pool.control

import java.text.{DecimalFormat, NumberFormat}

import math.BigDecimal.double2bigDecimal

import scalafx.geometry.Insets
import scalafx.scene.control.{Slider, TextField, TextFormatter}
import scalafx.scene.layout.HBox
import scalafx.util.converter.FormatStringConverter

import pool.{Context, Measurement}

class TextSlider(textFieldText: String,
                 formatConverter: FormatStringConverter[Number]) extends HBox:
  import TextSlider.*

  val slider = new Slider:
    prefWidth = 600
    showTickLabels = true
    showTickMarks = true
    snapToTicks = true
    minorTickCount = 0

  val textField = new TextField:
    style = "-fx-background-color: #ececec;"
    editable = false
    prefWidth = 50
    text = textFieldText
    textFormatter = new TextFormatter[Number](formatConverter):
      value <== slider.value

  spacing = 3
  padding = Insets(6)
  children = List(textField, slider)

  def valueAsDouble: Double = slider.value.toDouble
  def valueAsInt: Int = slider.value.toInt

object TextSlider:
  def decimalFormat: DecimalFormat = DecimalFormat("####.#")
  def integerFormat: NumberFormat = NumberFormat.getIntegerInstance

  def formatConverter(format: DecimalFormat): FormatStringConverter[Number] = new FormatStringConverter[Number](format)
  def formatConverter(format: NumberFormat): FormatStringConverter[Number] = new FormatStringConverter[Number](format)

  def totalChlorineTextSlider(measurement: Measurement): TextSlider =
    new TextSlider(textFieldText = measurement.totalChlorine.toString,
                   formatConverter = formatConverter(integerFormat)):
                     slider.min = 0
                     slider.max = 10
                     slider.majorTickUnit = 1
                     slider.value = measurement.totalChlorine

  def freeChlorineTextSlider(measurement: Measurement): TextSlider =
    new TextSlider(textFieldText = measurement.freeChlorine.toString,
                   formatConverter = formatConverter(integerFormat)):
                     slider.min = 0
                     slider.max = 10
                     slider.majorTickUnit = 1
                     slider.value = measurement.freeChlorine

  def combinedChlorineTextSlider(measurement: Measurement): TextSlider =
    new TextSlider(textFieldText = measurement.combinedChlorine.toString,
                   formatConverter = formatConverter(decimalFormat)):
                     slider.min = 0.0
                     slider.max = 0.5
                     slider.majorTickUnit = 0.1
                     slider.value = measurement.combinedChlorine

  def phTextSlider(measurement: Measurement): TextSlider =
    new TextSlider(textFieldText = measurement.ph.toString,
                   formatConverter = formatConverter(decimalFormat)):
                     slider.min = 6.2
                     slider.max = 8.4
                     slider.majorTickUnit = 0.1
                     slider.value = measurement.ph

  def calciumHardnessTextSlider(measurement: Measurement): TextSlider =
    new TextSlider(textFieldText = measurement.calciumHardness.toString,
                   formatConverter = formatConverter(integerFormat)):
                     slider.min = 0
                     slider.max = 1000
                     slider.majorTickUnit = 100
                     slider.snapToTicks = false
                     slider.value = measurement.calciumHardness

  def totalAlkalinityTextSlider(measurement: Measurement): TextSlider =
    new TextSlider(textFieldText = measurement.totalAlkalinity.toString,
                   formatConverter = formatConverter(integerFormat)):
                     slider.min = 0
                     slider.max = 240
                     slider.majorTickUnit = 20
                     slider.snapToTicks = false
                     slider.value = measurement.totalAlkalinity

  def cyanuricAcidTextSlider(measurement: Measurement): TextSlider =
    new TextSlider(textFieldText = measurement.cyanuricAcid.toString,
                   formatConverter = formatConverter(integerFormat)):
                     slider.min = 0
                     slider.max = 300
                     slider.majorTickUnit = 30
                     slider.snapToTicks = false
                     slider.value = measurement.cyanuricAcid

  def totalBromineTextSlider(measurement: Measurement): TextSlider =
    new TextSlider(textFieldText = measurement.totalBromine.toString,
                   formatConverter = formatConverter(integerFormat)):
                     slider.min = 0
                     slider.max = 20
                     slider.majorTickUnit = 1
                     slider.value = measurement.totalBromine

  def saltTextSlider(measurement: Measurement): TextSlider =
    new TextSlider(textFieldText = measurement.salt.toString,
                   formatConverter = formatConverter(integerFormat)):
                     slider.min = 0
                     slider.max = 3600
                     slider.majorTickUnit = 300
                     slider.snapToTicks = false
                     slider.value = measurement.salt

  def temperatureTextSlider(measurement: Measurement): TextSlider =
    new TextSlider(textFieldText = measurement.temperature.toString,
                   formatConverter = formatConverter(integerFormat)):
                     slider.min = 50
                     slider.max = 100
                     slider.majorTickUnit = 1
                     slider.value = measurement.totalBromine