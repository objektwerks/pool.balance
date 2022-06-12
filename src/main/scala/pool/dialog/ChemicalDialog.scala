package pool.dialog

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, ComboBox, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Context, Entity, Chemical}
import pool.unitOfMeasure
import pool.Entity.*

class ChemicalDialog(context: Context, chemical: Chemical) extends Dialog[Chemical]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogChemical