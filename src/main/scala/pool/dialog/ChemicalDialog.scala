package pool.dialog

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, ComboBox, DatePicker, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Context, Entity, Chemical}
import pool.unitOfMeasure
import pool.Entity.*

class ChemicalDialog(context: Context, chemical: Chemical) extends Dialog[Chemical]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogChemical

  val typeofComboBox = new ComboBox[String] {
  	items = ObservableBuffer.from(context.listChemicals)
  	value = chemical.typeof.toString
  }

  val amountTextField = new TextField {
    text = chemical.amount.toString
  }

  val unitComboBox = new ComboBox[String] {
  	items = ObservableBuffer.from(context.listUnits)
  	value = chemical.unit.toString
  }

  val addedDatePicker = new DatePicker {
    value = chemical.added.toLocalDate
  }

  val controls = List[(String, Region)](
    context.labelTypeof -> typeofComboBox,
    context.labelAmount -> amountTextField,
    context.labelUnit -> unitComboBox,
    context.labelAdded -> addedDatePicker
  )
  val pane = dialogPane()
  pane.content = ControlGridPane(controls)

  val saveButtonType = new ButtonType(context.buttonSave, ButtonData.OKDone)
  pane.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  val saveButton = pane.lookupButton(saveButtonType)

