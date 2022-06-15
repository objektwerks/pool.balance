package pool.control

import java.text.NumberFormat

import math.BigDecimal.double2bigDecimal

import scalafx.util.converter.FormatStringConverter

/**
  * free chlorine (fc): 0 - 10, ok = 1 - 5, ideal = 3
  * combined chlorine (cc = tc - fc): 0 - 0.5, ok = 0.2, ideal = 0
  * total chlorine (tc = fc + cc): 0 - 10, ok = 1 - 5, ideal = 3
  * ph: 6.2 - 8.4, ok = 7.2 - 7.6, ideal = 7.4
  * calcium hardness (ch): 0 - 1000, ok = 250 - 500, ideal = 375
  * total alkalinity (ta): 0 - 240, ok = 80 - 120, ideal = 100
  * cyanuric acid (cya): 0 - 300, ok = 30 - 100, ideal = 50
  * total bromine (tb): 0 - 20, ok = 2 - 10, ideal = 5
  * temperature: 50 - 110
 */
object Sliders:
  val freeChlorineRange = 0 to 10
  val combinedChlorineRange = 0.0 to 0.5
  val totalChlorineRange = 0 to 10
  val phRange = 6.2 to 8.4
  val calciumHardnessRange = 1 to 1000
  val totalAlkalinityRange = 0 to 240
  val cyanuricAcidRange = 0 to 300
  val totalBromineRange = 0 to 20
  val temperatureRange = 50 to 110

  def integerInstance = NumberFormat.getIntegerInstance
  def formatConverter(format: NumberFormat) = new FormatStringConverter[Number](format)