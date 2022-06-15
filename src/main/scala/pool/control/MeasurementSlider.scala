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
  import MeasurementSlider.*

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

object MeasurementSlider:
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

  def combinedChlorineSlider(context: Context,
                             measurement: Measurement): HBox =
    new MeasurementSlider(labelText = context.labelCombinedChlorine,
                          textFieldText = measurement.combinedChlorine.toString,
                          formatConverter = formatConverter(decimalFormat)) {
                            slider.min = 0.0
                            slider.max = 0.5
                            slider.majorTickUnit = 0.1
                            slider.value = measurement.combinedChlorine
                          }

  def totalChlorineSlider(context: Context,
                          measurement: Measurement): HBox =
    new MeasurementSlider(labelText = context.labelTotalChlorine,
                          textFieldText = measurement.totalChlorine.toString,
                          formatConverter = formatConverter(integerFormat)) {
                            slider.min = 0
                            slider.max = 10
                            slider.majorTickUnit = 1
                            slider.value = measurement.totalChlorine
                          }

  def phSlider(context: Context,
               measurement: Measurement): HBox =
    new MeasurementSlider(labelText = context.labelPh,
                          textFieldText = measurement.ph.toString,
                          formatConverter = formatConverter(decimalFormat)) {
                            slider.min = 6.2
                            slider.max = 8.4
                            slider.majorTickUnit = 0.1
                            slider.value = measurement.ph
                          }

  def calciumHardnessSlider(context: Context,
                            measurement: Measurement): HBox =
    new MeasurementSlider(labelText = context.labelCalciumHardness,
                          textFieldText = measurement.calciumHardness.toString,
                          formatConverter = formatConverter(integerFormat)) {
                            slider.min = 0
                            slider.max = 1000
                            slider.majorTickUnit = 100
                            slider.value = measurement.calciumHardness
                          }

  def totalAlkalinitySlider(context: Context,
                            measurement: Measurement): HBox =
    new MeasurementSlider(labelText = context.labelTotalAlkalinity,
                          textFieldText = measurement.totalAlkalinity.toString,
                          formatConverter = formatConverter(integerFormat)) {
                            slider.min = 0
                            slider.max = 240
                            slider.majorTickUnit = 20
                            slider.value = measurement.totalAlkalinity
                          }

  def cyanuricAcidSlider(context: Context,
                         measurement: Measurement): HBox =
    new MeasurementSlider(labelText = context.labelCyanuricAcid,
                          textFieldText = measurement.cyanuricAcid.toString,
                          formatConverter = formatConverter(integerFormat)) {
                            slider.min = 0
                            slider.max = 300
                            slider.majorTickUnit = 30
                            slider.value = measurement.totalAlkalinity
                          }

  def totalBromineSlider(context: Context,
                         measurement: Measurement): HBox =
    new MeasurementSlider(labelText = context.labelTotalBromine,
                          textFieldText = measurement.totalBromine.toString,
                          formatConverter = formatConverter(integerFormat)) {
                            slider.min = 0
                            slider.max = 20
                            slider.majorTickUnit = 1
                            slider.value = measurement.totalBromine
                          }

  def temperatureSlider(context: Context,
                        measurement: Measurement): HBox =
    new MeasurementSlider(labelText = context.labelTemperature,
                          textFieldText = measurement.temperature.toString,
                          formatConverter = formatConverter(integerFormat)) {
                            slider.min = 50
                            slider.max = 100
                            slider.majorTickUnit = 1
                            slider.value = measurement.totalBromine
                          }