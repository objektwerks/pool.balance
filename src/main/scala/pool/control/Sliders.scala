package pool.control

import java.text.NumberFormat

import scalafx.util.converter.FormatStringConverter

object Sliders:
  def integerInstance = NumberFormat.getIntegerInstance
  def formatConverter(format: NumberFormat) = new FormatStringConverter[Number](format)