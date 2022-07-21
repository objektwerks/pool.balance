package pool.dialog

import scalafx.Includes.*
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, DatePicker, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Context, Entity, Measurement, UnitOfMeasure}
import pool.Entity.*
import pool.control.TextSlider.*

final class MeasurementDialog(context: Context, measurement: Measurement) extends Dialog[Measurement]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogMeasurement

  val totalChlorineSlider = totalChlorineTextSlider(measurement)
  val freeChlorineSlider = freeChlorineTextSlider(measurement)
  val combinedChlorineSlider = combinedChlorineTextSlider(measurement)
  val phSlider = phTextSlider(measurement)
  val calciumHardnessSlider = calciumHardnessTextSlider(measurement)
  val totalAlkalinitySlider = totalAlkalinityTextSlider(measurement)
  val cyanuricAcidSlider =cyanuricAcidTextSlider(measurement)
  val totalBromineSlider = totalBromineTextSlider(measurement)
  val saltSlider = saltTextSlider(measurement)
  val temperatureSlider = temperatureTextSlider(measurement)
  val measuredDatePicker = new DatePicker:
    value = measurement.measured.toLocalDate

  val controls = List[(String, Region)](
    context.labelTotalChlorine -> totalChlorineSlider,
    context.labelFreeChlorine -> freeChlorineSlider,
    context.labelCombinedChlorine -> combinedChlorineSlider,
    context.labelPh -> phSlider,
    context.labelCalciumHardness -> calciumHardnessSlider,
    context.labelTotalAlkalinity -> totalAlkalinitySlider,
    context.labelCyanuricAcid -> cyanuricAcidSlider,
    context.labelTotalBromine -> totalBromineSlider,
    context.labelSalt -> saltSlider,
    context.labelTemperature -> temperatureSlider,
    context.labelMeasure -> measuredDatePicker
  )
  dialogPane().content = ControlGridPane(controls)

  val saveButtonType = new ButtonType(context.buttonSave, ButtonData.OKDone)
  dialogPane().buttonTypes = List(saveButtonType, ButtonType.Cancel)

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