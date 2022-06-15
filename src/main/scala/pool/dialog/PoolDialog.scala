package pool.dialog

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, ComboBox, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Context, Entity, Pool}
import pool.UnitOfMeasure
import pool.Entity.*

class PoolDialog(context: Context, pool: Pool) extends Dialog[Pool]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogPool

  val nameTextField = new TextField {
    text <==> pool.nameProperty
  }

  val builtTextField = new TextField {
    text <==> pool.builtProperty
  }

  val volumeTextField = new TextField {
    text <==> pool.volumeProperty
  }
  
  val unitComboBox = new ComboBox[String] {
  	items = ObservableBuffer.from(context.listUnits)
  	value <==> pool.unitProperty
  }

  val controls = List[(String, Region)](
    context.labelName -> nameTextField,
    context.labelBuilt -> builtTextField,
    context.labelVolume -> volumeTextField,
    context.labelUnit -> unitComboBox
  )
  val pane = dialogPane()
  pane.content = ControlGridPane(controls)

  val saveButtonType = new ButtonType(context.buttonSave, ButtonData.OKDone)
  pane.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  val saveButton = pane.lookupButton(saveButtonType)

  resultConverter = dialogButton => {
    if dialogButton == saveButtonType then pool
    else null
  }