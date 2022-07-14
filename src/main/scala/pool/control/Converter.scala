package pool.control

import scalafx.Includes.*
import scalafx.geometry.Insets
import scalafx.scene.control.Label
import scalafx.scene.layout.GridPane

import pool.Context
import pool.UnitOfMeasure.*

class Converter(context: Context) extends GridPane:
  hgap = 6
  vgap = 6
  padding = Insets(top = 6, right = 6, bottom = 6, left = 6)
  prefWidth = 200

  val gallonsTextField = new DoubleTextField:
    text = "1.0"
  val gallonsTextFieldResult = new DoubleTextField:
    text = ""
  val gallonsLabel = new Label:
    text = context.converterGallons

  val litersTextField = new DoubleTextField:
    text = "1.0"
  val litersTextFieldResult = new DoubleTextField:
    text = ""
  val litersLabel = new Label:
    text = context.converterLiters

  val poundsTextField = new DoubleTextField:
    text = "1.0"
  val poundsTextFieldResult = new DoubleTextField:
    text = ""
  val poundsLabel = new Label:
    text = context.converterPounds

  val kilogramsTextField = new DoubleTextField:
    text = "1.0"
  val kilogramsTextFieldResult = new DoubleTextField:
    text = ""
  val kilogramsLabel = new Label:
    text = context.converterKilograms

  gallonsTextField.text.onChange { (_, oldValue, newValue) =>
    println(s"gallons old: $oldValue new: $newValue")
    litersTextFieldResult.text = gallonsToLiters( newValue.toDoubleOption.getOrElse(1.0) ).toString
  }

  litersTextField.text.onChange { (_, oldValue, newValue) =>
    println(s"liters old: $oldValue new: $newValue")
    gallonsTextFieldResult.text = litersToGallons( newValue.toDoubleOption.getOrElse(1.0) ).toString
  }

  poundsTextField.text.onChange { (_, oldValue, newValue) =>
    println(s"pounds old: $oldValue new: $newValue")
    kilogramsTextFieldResult.text = poundsToKilograms( newValue.toDoubleOption.getOrElse(1.0) ).toString
  }

  kilogramsTextField.text.onChange { (_, oldValue, newValue) =>
    println(s"kilograms old: $oldValue new: $newValue")
    poundsTextFieldResult.text = kilogramsToPounds( newValue.toDoubleOption.getOrElse(1.0) ).toString
  }

  add(gallonsTextField, columnIndex = 0, rowIndex = 0)
  add(litersTextFieldResult, columnIndex = 1, rowIndex = 0)

  add(gallonsLabel, columnIndex = 0, rowIndex = 1)
  add(litersLabel, columnIndex = 1, rowIndex = 1)

  add(litersTextField, columnIndex = 0, rowIndex = 2)
  add(gallonsTextFieldResult, columnIndex = 1, rowIndex = 2)

  add(litersLabel, columnIndex = 0, rowIndex = 3)
  add(gallonsLabel, columnIndex = 1, rowIndex = 3)

  add(poundsTextField, columnIndex = 0, rowIndex = 4)
  add(kilogramsTextFieldResult, columnIndex = 1, rowIndex = 4)

  add(poundsLabel, columnIndex = 0, rowIndex = 5)
  add(kilogramsLabel, columnIndex = 1, rowIndex = 5)

  add(kilogramsTextField, columnIndex = 0, rowIndex = 6)
  add(poundsTextFieldResult, columnIndex = 1, rowIndex = 6)

  add(kilogramsLabel, columnIndex = 0, rowIndex = 7)
  add(poundsLabel, columnIndex = 1, rowIndex = 7)