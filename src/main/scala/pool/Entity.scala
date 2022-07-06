package pool

import java.time.{LocalDate, LocalTime, LocalDateTime}
import java.time.format.DateTimeFormatter

import math.BigDecimal.double2bigDecimal

import scalafx.beans.property.ObjectProperty
import ch.qos.logback.core.subst.Token.Type

final case class Error(message: String, occurred: LocalDateTime = LocalDateTime.now):
  val messageProperty = ObjectProperty[String](this, "message", message)
  val occurredProperty = ObjectProperty[String](this, "occurred", Error.format(occurred))

object Error:
  def format(localDateTime: LocalDateTime): String = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm"))

enum UnitOfMeasure:
  case gl, l, lb, kg, tablet

object UnitOfMeasure:
  def toList: List[String] = UnitOfMeasure.values.map(uom => uom.toString).toList
  def toPoolList: List[String] = List( UnitOfMeasure.gl.toString, UnitOfMeasure.l.toString )
  def gallonsToLiters(gallons: Double): Double = gallons * 3.785
  def litersToGallons(liters: Double): Double = liters * 0.264
  def poundsToKilograms(pounds: Double): Double = pounds * 0.454
  def kilogramsToPounds(kilograms: Double): Double = kilograms * 2.205

sealed trait Entity:
  val id: Long

object Entity:
  private def dateFormatterInstance: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  private def timeFormatterInstance: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

  def format(localDateTime: LocalDateTime): String = localDateTime.format(dateFormatterInstance)
  def format(localDate: LocalDate): String = localDate.format(dateFormatterInstance)
  def format(localTime: LocalTime): String = localTime.format(timeFormatterInstance)

  def applyLocalDate(localDate: LocalDate, localDateTime: LocalDateTime): LocalDateTime =
    localDateTime
      .withYear(localDate.getYear)
      .withMonth(localDate.getMonthValue)
      .withDayOfMonth(localDate.getDayOfMonth)

  def isNotInt(text: String): Boolean = !text.matches("\\d+")
  def isNotDouble(text: String): Boolean = !text.matches("\\d{0,7}([\\.]\\d{0,4})?")

  given poolOrdering: Ordering[Pool] = Ordering.by[Pool, String](p => p.name).reverse
  given cleaningOrdering: Ordering[Cleaning] = Ordering.by[Cleaning, Long](c => c.cleaned.toLocalDate.toEpochDay).reverse
  given measurementOrdering: Ordering[Measurement] = Ordering.by[Measurement, Long](m => m.measured.toLocalDate.toEpochDay).reverse
  given chemicalOrdering: Ordering[Chemical] = Ordering.by[Chemical, Long](c => c.added.toLocalDate.toEpochDay).reverse

final case class Pool(id: Long = 0,
                      name: String = "", 
                      volume: Int = 0,
                      unit: UnitOfMeasure = UnitOfMeasure.gl) extends Entity:
  val nameProperty = ObjectProperty[String](this, "name", name)
  val volumeProperty = ObjectProperty[Int](this, "volume", volume)
  val unitProperty = ObjectProperty[String](this, "unit", unit.toString)
  val pool = this

final case class Cleaning(id: Long = 0,
                          poolId: Long,
                          brush: Boolean = true,
                          net: Boolean = true,
                          skimmerBasket: Boolean = true,
                          pumpBasket: Boolean = false,
                          pumpFilter: Boolean = false,
                          vacuum: Boolean = false,
                          cleaned: LocalDateTime = LocalDateTime.now) extends Entity:
  val brushProperty = ObjectProperty[Boolean](this, "brush", brush)
  val netProperty = ObjectProperty[Boolean](this, "net", net)
  val skimmerBasketProperty = ObjectProperty[Boolean](this, "skimmerBasket", skimmerBasket)
  val pumpBasketProperty = ObjectProperty[Boolean](this, "pumpBasket", pumpBasket)
  val pumpFilterProperty = ObjectProperty[Boolean](this, "pumpFilter", pumpFilter)
  val vacuumProperty = ObjectProperty[Boolean](this, "vacuum", vacuum)
  val cleanedProperty = ObjectProperty[String](this, "cleaned", Entity.format(cleaned))
  val cleaning = this

final case class Measurement(id: Long = 0,
                             poolId: Long,
                             totalChlorine: Int = 3,
                             freeChlorine: Int = 3,
                             combinedChlorine: Double = 0.0,
                             ph: Double = 7.4,
                             calciumHardness: Int = 375,
                             totalAlkalinity: Int = 100,
                             cyanuricAcid: Int = 50,
                             totalBromine: Int = 5,
                             salt: Int = 3200,
                             temperature: Int = 85,
                             measured: LocalDateTime = LocalDateTime.now) extends Entity:
  val totalChlorineProperty = ObjectProperty[Int](this, "totalChlorine", totalChlorine)
  val freeChlorineProperty = ObjectProperty[Int](this, "freeChlorine", freeChlorine)
  val combinedChlorineProperty = ObjectProperty[Double](this, "combinedChlorine", combinedChlorine)
  val phProperty = ObjectProperty[Double](this, "ph", ph)
  val calciumHardnessProperty = ObjectProperty[Int](this, "calciumHardness", calciumHardness)
  val totalAlkalinityProperty = ObjectProperty[Int](this, "totalAlkalinity", totalAlkalinity)
  val cyanuricAcidProperty = ObjectProperty[Int](this, "cyanuricAcid", cyanuricAcid)
  val totalBromineProperty = ObjectProperty[Int](this, "totalBromine", totalBromine)
  val saltProperty = ObjectProperty[Int](this, "salt", salt)
  val temperatureProperty = ObjectProperty[Int](this, "temperature", temperature)
  val measuredProperty = ObjectProperty[String](this, "measured", Entity.format(measured))
  val measurement = this

enum TypeOfChemical(val display: String):
  case LiquidChlorine extends TypeOfChemical("Liquid Chlorine")
  case Trichlor extends TypeOfChemical("Trichlor")
  case Dichlor extends TypeOfChemical("Dichlor")
  case CalciumHypochlorite extends TypeOfChemical("Calcium Hypochlorite")
  case Stabilizer extends TypeOfChemical("Stabilizer")
  case Algaecide extends TypeOfChemical("Algaecide")
  case MuriaticAcid extends TypeOfChemical("Muriatic Acid")
  case Salt extends TypeOfChemical("Salt")

object TypeOfChemical:
  def toEnum(display: String): TypeOfChemical = TypeOfChemical.valueOf(display.filterNot(_.isWhitespace))
  def toList: List[String] = TypeOfChemical.values.map(toc => toc.display).toList

final case class Chemical(id: Long = 0,
                          poolId: Long,
                          typeof: TypeOfChemical = TypeOfChemical.LiquidChlorine,
                          amount: Double = 1.0, 
                          unit: UnitOfMeasure = UnitOfMeasure.gl,
                          added: LocalDateTime = LocalDateTime.now) extends Entity:
  val typeofProperty = ObjectProperty[String](this, "typeof", typeof.display)
  val amountProperty = ObjectProperty[Double](this, "amount", amount)
  val unitProperty = ObjectProperty[String](this, "unit", unit.toString)
  val addedProperty = ObjectProperty[String](this, "added", Entity.format(added))
  val chemical = this