package pool.control

import java.text.{DecimalFormat, NumberFormat}

import math.BigDecimal.double2bigDecimal

import scalafx.scene.control.{Slider, TextField, TextFormatter}
import scalafx.scene.layout.HBox
import scalafx.util.converter.FormatStringConverter

import pool.{Context, Measurement}

class TextFieldSlider(textFieldText: String,
                      formatConverter: FormatStringConverter[Number]) extends HBox:
  import TextFieldSlider.*

  spacing = 3
  children = List(textField, slider)

  val slider = new Slider {
    showTickLabels = true
    showTickMarks = true
  }

  val textField = new TextField {
    text = textFieldText
    textFormatter = new TextFormatter[Number](formatConverter) {
      value <==> slider.value
    }
  }

  def asDouble: Double = slider.value.toDouble
  def asInt: Int = slider.value.toInt

object TextFieldSlider:
  def decimalFormat: DecimalFormat = DecimalFormat("####.#")
  def integerFormat: NumberFormat = NumberFormat.getIntegerInstance

  def formatConverter(format: DecimalFormat): FormatStringConverter[Number] = new FormatStringConverter[Number](format)
  def formatConverter(format: NumberFormat): FormatStringConverter[Number] = new FormatStringConverter[Number](format)

  def freeChlorineTextFieldSlider(context: Context,
                                  measurement: Measurement): TextFieldSlider =
    new TextFieldSlider(textFieldText = measurement.freeChlorine.toString,
                        formatConverter = formatConverter(integerFormat)) {
                          slider.min = 0
                          slider.max = 10
                          slider.majorTickUnit = 1
                          slider.value = measurement.freeChlorine
                        }

  def combinedChlorineTextFieldSlider(context: Context,
                                      measurement: Measurement): TextFieldSlider =
    new TextFieldSlider(textFieldText = measurement.combinedChlorine.toString,
                        formatConverter = formatConverter(decimalFormat)) {
                          slider.min = 0.0
                          slider.max = 0.5
                          slider.majorTickUnit = 0.1
                          slider.value = measurement.combinedChlorine
                        }

  def totalChlorineTextFieldSlider(context: Context,
                                   measurement: Measurement): TextFieldSlider =
    new TextFieldSlider(textFieldText = measurement.totalChlorine.toString,
                        formatConverter = formatConverter(integerFormat)) {
                          slider.min = 0
                          slider.max = 10
                          slider.majorTickUnit = 1
                          slider.value = measurement.totalChlorine
                        }

  def phTextFieldSlider(context: Context,
                        measurement: Measurement): TextFieldSlider =
    new TextFieldSlider(textFieldText = measurement.ph.toString,
                        formatConverter = formatConverter(decimalFormat)) {
                          slider.min = 6.2
                          slider.max = 8.4
                          slider.majorTickUnit = 0.1
                          slider.value = measurement.ph
                        }

  def calciumHardnessTextFieldSlider(context: Context,
                                     measurement: Measurement): TextFieldSlider =
    new TextFieldSlider(textFieldText = measurement.calciumHardness.toString,
                        formatConverter = formatConverter(integerFormat)) {
                          slider.min = 0
                          slider.max = 1000
                          slider.majorTickUnit = 100
                          slider.value = measurement.calciumHardness
                        }

  def totalAlkalinityTextFieldSlider(context: Context,
                                     measurement: Measurement): TextFieldSlider =
    new TextFieldSlider(textFieldText = measurement.totalAlkalinity.toString,
                        formatConverter = formatConverter(integerFormat)) {
                          slider.min = 0
                          slider.max = 240
                          slider.majorTickUnit = 20
                          slider.value = measurement.totalAlkalinity
                        }

  def cyanuricAcidTextFieldSlider(context: Context,
                                  measurement: Measurement): TextFieldSlider =
    new TextFieldSlider(textFieldText = measurement.cyanuricAcid.toString,
                        formatConverter = formatConverter(integerFormat)) {
                          slider.min = 0
                          slider.max = 300
                          slider.majorTickUnit = 30
                          slider.value = measurement.totalAlkalinity
                        }

  def totalBromineTextFieldSlider(context: Context,
                                  measurement: Measurement): TextFieldSlider =
    new TextFieldSlider(textFieldText = measurement.totalBromine.toString,
                        formatConverter = formatConverter(integerFormat)) {
                          slider.min = 0
                          slider.max = 20
                          slider.majorTickUnit = 1
                          slider.value = measurement.totalBromine
                        }

  def temperatureTextFieldSlider(context: Context,
                                 measurement: Measurement): TextFieldSlider =
    new TextFieldSlider(textFieldText = measurement.temperature.toString,
                        formatConverter = formatConverter(integerFormat)) {
                          slider.min = 50
                          slider.max = 100
                          slider.majorTickUnit = 1
                          slider.value = measurement.totalBromine
                        }