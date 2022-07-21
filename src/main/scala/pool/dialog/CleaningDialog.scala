package pool.dialog

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, CheckBox, DatePicker, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Context, Entity, Cleaning, UnitOfMeasure}
import pool.Entity.*

final class CleaningDialog(context: Context, cleaning: Cleaning) extends Dialog[Cleaning]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogCleaning

  val brushCheckBox = new CheckBox:
    selected = cleaning.brush

  val netCheckBox = new CheckBox:
    selected = cleaning.net

  val skimmerBasketCheckBox = new CheckBox:
    selected = cleaning.skimmerBasket

  val pumpBasketCheckBox = new CheckBox:
    selected = cleaning.pumpBasket

  val pumpFilterCheckBox = new CheckBox:
    selected = cleaning.pumpFilter

  val vacuumCheckBox = new CheckBox:
    selected = cleaning.vacuum

  val cleanedDatePicker = new DatePicker:
    value = cleaning.cleaned.toLocalDate

  val controls = List[(String, Region)](
    context.labelBrush -> brushCheckBox,
    context.labelNet -> netCheckBox,
    context.labelSkimmerBasket -> skimmerBasketCheckBox,
    context.labelPumpBasket -> pumpBasketCheckBox,
    context.labelPumpFilter -> pumpFilterCheckBox,
    context.labelVacuum -> vacuumCheckBox,
    context.labelCleaned -> cleanedDatePicker
  )
  dialogPane().content = ControlGridPane(controls)

  val saveButtonType = new ButtonType(context.buttonSave, ButtonData.OKDone)
  dialogPane().buttonTypes = List(saveButtonType, ButtonType.Cancel)

  resultConverter = dialogButton => {
    if dialogButton == saveButtonType then
      cleaning.copy(
        brush = brushCheckBox.selected.value,
        net = netCheckBox.selected.value,
        skimmerBasket = skimmerBasketCheckBox.selected.value,
        pumpBasket = pumpBasketCheckBox.selected.value,
        pumpFilter = pumpFilterCheckBox.selected.value,
        vacuum = vacuumCheckBox.selected.value,
        cleaned = applyLocalDate(cleanedDatePicker.value.value, cleaning.cleaned)
      )
    else null
  }