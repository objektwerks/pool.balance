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

  val freeChlorineSlider = freeChlorineTextFieldSlider(context, measurement)
  val combinedChlorineSlider = combinedChlorineTextFieldSlider(context, measurement)
  val totalChlorineSlider = totalChlorineTextFieldSlider(context, measurement)
  val phSlider = phTextFieldSlider(context, measurement)
  val calciumHardnessSlider = calciumHardnessTextFieldSlider(context, measurement)
  val totalAlkalinitySlider = totalAlkalinityTextFieldSlider(context, measurement)
  val cyanuricAcidSlider =cyanuricAcidTextFieldSlider(context, measurement)
  val totalBromineSlider = totalBromineTextFieldSlider(context, measurement)
  val temperatureSlider = temperatureTextFieldSlider(context, measurement)
  val measuredDatePicker = new DatePicker {
    value = measurement.measured.toLocalDate
  }

  val controls = List[(String, Region)](
    context.labelFreeChlorine -> freeChlorineSlider,
    context.labelCombinedChlorine -> combinedChlorineSlider,
    context.labelTotalChlorine -> totalChlorineSlider,
    context.labelPh -> phSlider,
    context.labelCalciumHardness -> calciumHardnessSlider,
    context.labelTotalAlkalinity -> totalAlkalinitySlider,
    context.labelCyanuricAcid -> cyanuricAcidSlider,
    context.labelTotalBromine -> totalBromineSlider,
    context.labelTemperature -> temperatureSlider,
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
        freeChlorine = freeChlorineSlider.valueAsInt,
        combinedChlorine = combinedChlorineSlider.valueAsDouble,
        totalChlorine = totalChlorineSlider.valueAsInt,
        ph = phSlider.valueAsDouble,
        calciumHardness = calciumHardnessSlider.valueAsInt,
        totalAlkalinity = totalAlkalinitySlider.valueAsInt,
        cyanuricAcid = cyanuricAcidSlider.valueAsInt,
        totalBromine = totalBromineSlider.valueAsInt,
        temperature = temperatureSlider.valueAsInt,
        measured = applyLocalDate(measuredDatePicker.value.value, measurement.measured)
      )
    else null
  }