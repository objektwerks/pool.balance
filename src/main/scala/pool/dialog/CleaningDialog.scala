package pool.dialog

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.Region
import scalafx.scene.control.{ButtonType, CheckBox, ComboBox, DatePicker, Dialog, TextField}
import scalafx.scene.control.ButtonBar.ButtonData

import pool.{App, Context, Entity, Cleaning}
import pool.UnitOfMeasure
import pool.Entity.*

class CleaningDialog(context: Context, cleaning: Cleaning) extends Dialog[Cleaning]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogCleaning

  val brushCheckBox = new CheckBox {
    selected = cleaning.brush
  }

  val netCheckBox = new CheckBox {
    selected = cleaning.net
  }

  val skimmerBasketCheckBox = new CheckBox {
    selected = cleaning.skimmerBasket
  }

  val pumpBasketCheckBox = new CheckBox {
    selected = cleaning.pumpBasket
  }

  val pumpFilterCheckBox = new CheckBox {
    selected = cleaning.pumpFilter
  }

  val vacuumCheckBox = new CheckBox {
    selected = cleaning.vacuum
  }

  val cleanedDatePicker = new DatePicker {
    value = cleaning.cleaned.toLocalDate
  }