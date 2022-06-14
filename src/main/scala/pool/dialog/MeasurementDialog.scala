package pool.dialog

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, ComboBox, DatePicker, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Context, Entity, Measurement}
import pool.UnitOfMeasure
import pool.Entity.*

class MeasurementDialog(context: Context, measurement: Measurement) extends Dialog[Measurement]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogMeasurement

  val freeChlorineTextField = new TextField {
    text = measurement.freeChlorine.toString
  }

  val combinedChlorineTextField = new TextField {
    text = measurement.combinedChlorine.toString
  }

  val totalChlorineTextField = new TextField {
    text = measurement.totalChlorine.toString
  }

  val phTextField = new TextField {
    text = measurement.ph.toString
  }

  val calciumHardnessTextField = new TextField {
    text = measurement.calciumHardness.toString
  }

  val totalAlkalinityTextField = new TextField {
    text = measurement.totalAlkalinity.toString
  }

  val cyanuricAcidTextField = new TextField {
    text = measurement.cyanuricAcid.toString
  }

  val totalBromineTextField = new TextField {
    text = measurement.totalBromine.toString
  }

  val temperatureTextField = new TextField {
    text = measurement.temperature.toString
  }

  val measuredDatePicker = new DatePicker {
    value = measurement.measured.toLocalDate
  }

  val controls = List[(String, Region)](
    context.labelFreeChlorine -> freeChlorineTextField,
    context.labelCombinedChlorine -> combinedChlorineTextField,
    context.labelTotalChlorine -> totalChlorineTextField,
    context.labelPh -> phTextField,
    context.labelCalciumHardness -> calciumHardnessTextField,
    context.labelTotalAlkalinity -> totalAlkalinityTextField,
    context.labelCyanuricAcid -> cyanuricAcidTextField,
    context.labelTotalBromine -> totalBromineTextField,
    context.labelTemperature -> temperatureTextField,
    context.labelMeasure -> measuredDatePicker
  )
  val pane = dialogPane()
  pane.content = ControlGridPane(controls)

  val saveButtonType = new ButtonType(context.buttonSave, ButtonData.OKDone)
  pane.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  val saveButton = pane.lookupButton(saveButtonType)

  freeChlorineTextField.text.onChange { (_, oldValue, newValue) => if isNotInt(newValue) then freeChlorineTextField.text.value = oldValue }
  combinedChlorineTextField.text.onChange { (_, oldValue, newValue) => if isNotInt(newValue) then combinedChlorineTextField.text.value = oldValue }
  totalChlorineTextField.text.onChange { (_, oldValue, newValue) => if isNotInt(newValue) then totalChlorineTextField.text.value = oldValue }
  phTextField.text.onChange { (_, oldValue, newValue) => if isNotInt(newValue) then phTextField.text.value = oldValue }
  calciumHardnessTextField.text.onChange { (_, oldValue, newValue) => if isNotInt(newValue) then calciumHardnessTextField.text.value = oldValue }
  totalAlkalinityTextField.text.onChange { (_, oldValue, newValue) => if isNotInt(newValue) then totalAlkalinityTextField.text.value = oldValue }
  cyanuricAcidTextField.text.onChange { (_, oldValue, newValue) => if isNotInt(newValue) then cyanuricAcidTextField.text.value = oldValue }
  totalBromineTextField.text.onChange { (_, oldValue, newValue) => if isNotInt(newValue) then totalBromineTextField.text.value = oldValue }
  temperatureTextField.text.onChange { (_, oldValue, newValue) => if isNotInt(newValue) then temperatureTextField.text.value = oldValue }
  measuredDatePicker.value.onChange { (_, _, newValue) => saveButton.disable = false }

