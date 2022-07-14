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

  val glTextField = new DoubleTextField:
    text = "0.0"

  val glLabel = new Label:
    text = context.converterGallons

  val lTextField = new DoubleTextField:
    text = "0.0"

  val lLabel = new Label:
    text = context.converterLiters

  val lbTextField = new DoubleTextField:
    text = "0.0"

  val lbLabel = new Label:
    text = context.converterPounds

  val kgTextField = new DoubleTextField:
    text = "0.0"

  val kgLabel = new Label:
    text = context.converterKilograms

  glTextField.textProperty.addListener { (_, _, newValue) =>
    lTextField.text = gallonsToLiters( newValue.toDouble ).toString
  }

  lTextField.textProperty.addListener { (_, _, newValue) =>
    glTextField.text = litersToGallons( newValue.toDouble ).toString
  }

  lbTextField.textProperty.addListener { (_, _, newValue) =>
    kgTextField.text = poundsToKilograms( newValue.toDouble ).toString

  }

  kgTextField.textProperty.addListener { (_, _, newValue) =>
    lbTextField.text = kilogramsToPounds( newValue.toDouble ).toString
  }

  add(glTextField, columnIndex = 0, rowIndex = 0)
  add(lTextField, columnIndex = 1, rowIndex = 0)

  add(glLabel, columnIndex = 0, rowIndex = 1)
  add(lLabel, columnIndex = 1, rowIndex = 1)

  add(lbTextField, columnIndex = 0, rowIndex = 2)
  add(kgTextField, columnIndex = 1, rowIndex = 2)

  add(lbLabel, columnIndex = 0, rowIndex = 3)
  add(kgLabel, columnIndex = 1, rowIndex = 3)