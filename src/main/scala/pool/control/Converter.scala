package pool.control

import scalafx.Includes.*
import scalafx.beans.property.ObjectProperty
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

  val gallonsProperty = ObjectProperty[Double](0.0)
  val litersProperty = ObjectProperty[Double](0.0)
  val poundsProperty = ObjectProperty[Double](0.0)
  val kilogramsProperty = ObjectProperty[Double](0.0)

  val gallonsTextField = new DoubleTextField:
    text <== gallonsProperty.asString

  val gallonsLabel = new Label:
    text = context.converterGallons

  val litersTextField = new DoubleTextField:
    text <== litersProperty.asString

  val litersLabel = new Label:
    text = context.converterLiters

  val poundsTextField = new DoubleTextField:
    text <== poundsProperty.asString

  val poundsLabel = new Label:
    text = context.converterPounds

  val kilogramsTextField = new DoubleTextField:
    text <== kilogramsProperty.asString

  val kilogramsLabel = new Label:
    text = context.converterKilograms

  gallonsTextField.text.onChange { (_, oldValue, newValue) =>
    println(s"gallons old: $oldValue new: $newValue")
    litersProperty.value = gallonsToLiters( newValue.toDoubleOption.getOrElse(1.0) )
  }

  litersTextField.text.onChange { (_, oldValue, newValue) =>
    println(s"liters old: $oldValue new: $newValue")
    gallonsProperty.value = litersToGallons( newValue.toDoubleOption.getOrElse(1.0) )
  }

  poundsTextField.text.onChange { (_, oldValue, newValue) =>
    println(s"pounds old: $oldValue new: $newValue")
    kilogramsProperty.value = poundsToKilograms( newValue.toDoubleOption.getOrElse(1.0) )
  }

  kilogramsTextField.text.onChange { (_, oldValue, newValue) =>
    println(s"kilograms old: $oldValue new: $newValue")
    poundsProperty.value = kilogramsToPounds( newValue.toDoubleOption.getOrElse(1.0) )
  }

  add(gallonsTextField, columnIndex = 0, rowIndex = 0)
  add(litersTextField, columnIndex = 1, rowIndex = 0)

  add(gallonsLabel, columnIndex = 0, rowIndex = 1)
  add(litersLabel, columnIndex = 1, rowIndex = 1)

  add(poundsTextField, columnIndex = 0, rowIndex = 2)
  add(kilogramsTextField, columnIndex = 1, rowIndex = 2)

  add(poundsLabel, columnIndex = 0, rowIndex = 3)
  add(kilogramsLabel, columnIndex = 1, rowIndex = 3)