package pool.dialog

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, ComboBox, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Context, Entity, Pool}
import pool.UnitOfMeasure
import pool.Entity.*
import pool.control.IntTextField

class PoolDialog(context: Context, pool: Pool) extends Dialog[Pool]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogPool

  val nameTextField = new TextField {
    text = pool.name
  }

  val volumeTextField = new IntTextField {
    text = pool.volume.toString
  }
  
  val unitComboBox = new ComboBox[String] {
  	items = ObservableBuffer.from(context.listPoolUnits)
  	value = pool.unit.toString
  }

  val controls = List[(String, Region)](
    context.labelName -> nameTextField,
    context.labelVolume -> volumeTextField,
    context.labelUnit -> unitComboBox
  )
  val pane = dialogPane()
  pane.content = ControlGridPane(controls)

  val saveButtonType = new ButtonType(context.buttonSave, ButtonData.OKDone)
  pane.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  val saveButton = pane.lookupButton(saveButtonType)

  resultConverter = dialogButton => {
    if dialogButton == saveButtonType then
      pool.copy(
        name = nameTextField.text.value,
        volume = volumeTextField.text.value.toIntOption.getOrElse(pool.volume),
        unit = UnitOfMeasure.valueOf(unitComboBox.value.value)
      )
    else null
  }