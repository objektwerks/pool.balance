package pool.dialog

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.{Region, VBox}
import scalafx.scene.control.{ButtonType, ComboBox, DatePicker, Dialog, Separator, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Chemical, Context, Entity, UnitOfMeasure, TypeOfChemical}
import pool.Entity.*
import pool.control.{Converter, DoubleTextField}

final class ChemicalDialog(context: Context, chemical: Chemical) extends Dialog[Chemical]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogChemical

  val typeofComboBox = new ComboBox[String]:
  	items = ObservableBuffer.from( TypeOfChemical.toList )
  	value = chemical.typeof.display
  typeofComboBox.prefWidth = 200

  val amountTextField = new DoubleTextField:
    prefWidth = 200
    text = chemical.amount.toString

  val unitComboBox = new ComboBox[String]:
  	items = ObservableBuffer.from( UnitOfMeasure.toList )
  	value = chemical.unit.toString
  unitComboBox.prefWidth = 200

  val addedDatePicker = new DatePicker:
    prefWidth = 200
    value = chemical.added.toLocalDate

  val controls = List[(String, Region)](
    context.labelTypeof -> typeofComboBox,
    context.labelAmount -> amountTextField,
    context.labelUnit -> unitComboBox,
    context.labelAdded -> addedDatePicker
  )
  val controlsPane = ControlGridPane(controls)
  val converterPane = Converter(context)
  
  dialogPane().content = new VBox:
    children = List(controlsPane, new Separator(), converterPane)

  val saveButtonType = new ButtonType(context.buttonSave, ButtonData.OKDone)
  dialogPane().buttonTypes = List(saveButtonType, ButtonType.Cancel)

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