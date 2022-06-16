package pool.dialog

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, ComboBox, DatePicker, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Context, Entity, Measurement}
import pool.UnitOfMeasure
import pool.Entity.*
import pool.control.TextFieldSlider.*

class MeasurementDialog(context: Context, measurement: Measurement) extends Dialog[Measurement]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogMeasurement

  val freeChlorineTextSlider = freeChlorineTextFieldSlider(context, measurement)
  val combinedChlorineTextSlider = combinedChlorineTextFieldSlider(context, measurement)
  val totalChlorineTextSlider = totalChlorineTextFieldSlider(context, measurement)
  val phTextSlider = phTextFieldSlider(context, measurement)
  val calciumHardnessTextSlider = calciumHardnessTextFieldSlider(context, measurement)
  val totalAlkalinityTextSlider = totalAlkalinityTextFieldSlider(context, measurement)
  val cyanuricAcidTextSlider =cyanuricAcidTextFieldSlider(context, measurement)
  val totalBromineTextSlider = totalBromineTextFieldSlider(context, measurement)
  val temperatureTextSlider = temperatureTextFieldSlider(context, measurement)
  val measuredDatePicker = new DatePicker {
    value = measurement.measured.toLocalDate
  }

  val controls = List[(String, Region)](
    context.labelFreeChlorine -> freeChlorineTextSlider,
    context.labelCombinedChlorine -> combinedChlorineTextSlider,
    context.labelTotalChlorine -> totalChlorineTextSlider,
    context.labelPh -> phTextSlider,
    context.labelCalciumHardness -> calciumHardnessTextSlider,
    context.labelTotalAlkalinity -> totalAlkalinityTextSlider,
    context.labelCyanuricAcid -> cyanuricAcidTextSlider,
    context.labelTotalBromine -> totalBromineTextSlider,
    context.labelTemperature -> temperatureTextSlider,
    context.labelMeasure -> measuredDatePicker
  )
  val pane = dialogPane()
  pane.content = ControlGridPane(controls)

  val saveButtonType = new ButtonType(context.buttonSave, ButtonData.OKDone)
  pane.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  val saveButton = pane.lookupButton(saveButtonType)

  resultConverter = dialogButton => {
    if dialogButton == saveButtonType then
      measurement.copy(
        freeChlorine = freeChlorineTextSlider.asInt,
        combinedChlorine = combinedChlorineTextSlider.asDouble,
        totalChlorine = totalChlorineTextSlider.asInt,
        ph = phTextSlider.asDouble,
        calciumHardness = calciumHardnessTextSlider.asInt,
        totalAlkalinity = totalAlkalinityTextSlider.asInt,
        cyanuricAcid = cyanuricAcidTextSlider.asInt,
        totalBromine = totalBromineTextSlider.asInt,
        temperature = temperatureTextSlider.asInt,
        measured = applyLocalDate(measuredDatePicker.value.value, measurement.measured)
      )
    else null
  }