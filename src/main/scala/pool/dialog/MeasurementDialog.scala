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

  resultConverter = dialogButton => {
    if dialogButton == saveButtonType then
      measurement.copy(
        freeChlorine = freeChlorineTextField.text.value.toIntOption.getOrElse(-1),
        combinedChlorine = combinedChlorineTextField.text.value.toIntOption.getOrElse(-1),
        totalChlorine = totalChlorineTextField.text.value.toIntOption.getOrElse(-1),
        ph = phTextField.text.value.toIntOption.getOrElse(-1),
        calciumHardness = calciumHardnessTextField.text.value.toIntOption.getOrElse(-1),
        totalAlkalinity = totalAlkalinityTextField.text.value.toIntOption.getOrElse(-1),
        cyanuricAcid = cyanuricAcidTextField.text.value.toIntOption.getOrElse(-1),
        totalBromine = totalBromineTextField.text.value.toIntOption.getOrElse(-1),
        temperature = temperatureTextField.text.value.toIntOption.getOrElse(-1),
        measured = applyLocalDate(measuredDatePicker.value.value, measurement.measured)
      )
    else null
  }