package pool.dialog

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{ComboBox, Dialog, TextField}

import pool.{Context, Pool}

class PoolDialog(context: Context, pool: Pool) extends Dialog[Pool]:
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