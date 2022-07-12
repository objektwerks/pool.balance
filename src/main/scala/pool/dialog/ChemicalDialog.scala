package pool.dialog

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, ComboBox, DatePicker, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Chemical, Context, Entity, UnitOfMeasure, TypeOfChemical}
import pool.Entity.*
import pool.control.DoubleTextField

class ChemicalDialog(context: Context, chemical: Chemical) extends Dialog[Chemical]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogChemical

  val typeofComboBox = new ComboBox[String] {
  	items = ObservableBuffer.from( TypeOfChemical.toList )
  	value = chemical.typeof.display
  }
  typeofComboBox.prefWidth = 200

  val amountTextField = new DoubleTextField {
    text = chemical.amount.toString
  }

  val unitComboBox = new ComboBox[String] {
  	items = ObservableBuffer.from( UnitOfMeasure.toList )
  	value = chemical.unit.toString
  }
  unitComboBox.prefWidth = 200

  val addedDatePicker = new DatePicker {
    value = chemical.added.toLocalDate
  }
  addedDatePicker.prefWidth = 200

  val controls = List[(String, Region)](
    context.labelTypeof -> typeofComboBox,
    context.labelAmount -> amountTextField,
    context.labelUnit -> unitComboBox,
    context.labelAdded -> addedDatePicker
  )
  val pane = dialogPane()
  pane.content = ControlGridPane(controls)

  val converterButtonType = new ButtonType(context.buttonConverter)
  val saveButtonType = new ButtonType(context.buttonSave, ButtonData.OKDone)
  pane.buttonTypes = List(saveButtonType, ButtonType.Cancel, converterButtonType)

  resultConverter = dialogButton => {
    if dialogButton == saveButtonType then
      chemical.copy(
        typeof = TypeOfChemical.toEnum(typeofComboBox.value.value),
        amount = amountTextField.text.value.toDoubleOption.getOrElse(chemical.amount),
        unit = UnitOfMeasure.valueOf(unitComboBox.value.value),
        added = applyLocalDate(addedDatePicker.value.value, chemical.added)
      )
    else null
  }