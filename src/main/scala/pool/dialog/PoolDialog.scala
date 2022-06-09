package pool.dialog

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, ComboBox, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Context, Entity, Pool}
import pool.unitOfMeasure

class PoolDialog(context: Context, pool: Pool) extends Dialog[Pool]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogHeaderPool

  val nameTextField = new TextField {
    text = pool.name
  }

  val builtTextField = new TextField {
    text = pool.built.toString
  }

  val volumeTextField = new TextField {
    text = pool.volume.toString
  }
  
  val unitComboBox = new ComboBox[String] {
  	items = ObservableBuffer.from(context.listUnits)
  	value = pool.unit.toString
  }

  val controls = List[(String, Region)](
    context.labelName -> nameTextField,
    context.labelBuilt -> builtTextField,
    context.labelVolume -> volumeTextField,
    context.labelUnit -> unitComboBox
  )

  val dialog = dialogPane()
  val saveButtonType = new ButtonType(context.buttonSave, ButtonData.OKDone)
  val saveButton = dialog.lookupButton(saveButtonType)
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = ControlGridPane(controls)
  
  nameTextField.text.onChange { (_, _, newValue) => saveButton.disable = newValue.trim.isEmpty }
  builtTextField.text.onChange { (_, oldValue, newValue) => if (Entity.isNotInt(newValue)) builtTextField.text.value = oldValue }
  volumeTextField.text.onChange { (_, oldValue, newValue) => if (Entity.isNotInt(newValue)) volumeTextField.text.value = oldValue }
  unitComboBox.value.onChange { (_, _, newValue) => saveButton.disable = newValue.trim.isEmpty }

  resultConverter = dialogButton => {
    if (dialogButton == saveButtonType)
      pool.copy(name = nameTextField.text.value,
      	        built = builtTextField.text.value.toIntOption.getOrElse(-1),
                volume = volumeTextField.text.value.toIntOption.getOrElse(-1),
                unit = unitOfMeasure.valueOf(unitComboBox.value.value))
    else null
  }