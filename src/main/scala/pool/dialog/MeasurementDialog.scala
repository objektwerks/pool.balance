package pool.dialog

import scalafx.Includes.*
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, DatePicker, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Context, Entity, Measurement, UnitOfMeasure}
import pool.Entity.*
import pool.control.TextSlider.*

class MeasurementDialog(context: Context, measurement: Measurement) extends Dialog[Measurement]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogMeasurement

  val totalChlorineSlider = totalChlorineTextSlider(context, measurement)
  val freeChlorineSlider = freeChlorineTextSlider(context, measurement)
  val combinedChlorineSlider = combinedChlorineTextSlider(context, measurement)
  val phSlider = phTextSlider(context, measurement)
  val calciumHardnessSlider = calciumHardnessTextSlider(context, measurement)
  val totalAlkalinitySlider = totalAlkalinityTextSlider(context, measurement)
  val cyanuricAcidSlider =cyanuricAcidTextSlider(context, measurement)
  val totalBromineSlider = totalBromineTextSlider(context, measurement)
  val temperatureSlider = temperatureTextSlider(context, measurement)
  val measuredDatePicker = new DatePicker {
    value = measurement.measured.toLocalDate
  }

  val controls = List[(String, Region)](
    context.labelTotalChlorine -> totalChlorineSlider,
    context.labelFreeChlorine -> freeChlorineSlider,
    context.labelCombinedChlorine -> combinedChlorineSlider,
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
        totalChlorine = totalChlorineSlider.valueAsInt,
        freeChlorine = freeChlorineSlider.valueAsInt,
        combinedChlorine = combinedChlorineSlider.valueAsDouble,
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