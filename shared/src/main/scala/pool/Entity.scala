package pool

import java.time.{Instant, LocalDate, LocalTime, LocalDateTime, ZoneId}
import java.time.format.DateTimeFormatter
import java.util.{Date, UUID}

import math.BigDecimal.double2bigDecimal

import scalafx.Includes.*
import scalafx.beans.property.ObjectProperty
import scala.util.Random

sealed trait Entity:
  val id: Long

object Entity:
  def instant: String = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault()).format(Instant.now)
  def parse(instant: String): Instant = Instant.parse(instant)
  def toInstant(localDate: LocalDate): Instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant
  def toLocalDate(instant: String): LocalDate = LocalDate.ofInstant(parse(instant), ZoneId.systemDefault())
  def toString(localDate: LocalDate): String = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault()).format(toInstant(localDate))

  def isNotInt(text: String): Boolean = !text.matches("\\d+")
  def isNotDouble(text: String): Boolean = !text.matches("\\d{0,7}([\\.]\\d{0,4})?")

  given poolOrdering: Ordering[Pool] = Ordering.by[Pool, String](p => p.name).reverse
  given cleaningOrdering: Ordering[Cleaning] = Ordering.by[Cleaning, Long](c => parse(c.cleaned).toEpochMilli).reverse
  given measurementOrdering: Ordering[Measurement] = Ordering.by[Measurement, Long](m => parse(m.measured).toEpochMilli).reverse
  given chemicalOrdering: Ordering[Chemical] = Ordering.by[Chemical, Long](c => parse(c.added).toEpochMilli).reverse

final case class Account(id: Long = 0,
                         license: String = newLicense,
                         emailAddress: String = "",
                         pin: String = newPin,
                         activated: String = Entity.instant,
                         deactivated: String = "") extends Entity:
  def toArray: Array[Any] = Array(id, license, pin, activated, deactivated)

object Account:
  private val specialChars = "~!@#$%^&*-+=<>?/:;".toList
  private val random = new Random

  private def newSpecialChar: Char = specialChars(random.nextInt(specialChars.length))

  /**
   * 26 letters + 10 numbers + 18 special characters = 54 combinations
   * 7 alphanumeric char pin = 54^7 ( 1,338,925,209,984 )
   */
  private def newPin: String =
    Random.shuffle(
      Random
        .alphanumeric
        .take(5)
        .mkString
        .prepended(newSpecialChar)
        .appended(newSpecialChar)
    ).mkString

  private def newLicense: String = UUID.randomUUID.toString

  val empty = Account(
    license = "",
    emailAddress = "",
    pin = "",
    activated = "",
    deactivated = ""
  )

final case class Pool(id: Long = 0,
                      name: String = "", 
                      volume: Int = 0,
                      unit: String = UnitOfMeasure.gl.toString) extends Entity:
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
                          cleaned: String = Entity.instant) extends Entity:
  val brushProperty = ObjectProperty[Boolean](this, "brush", brush)
  val netProperty = ObjectProperty[Boolean](this, "net", net)
  val skimmerBasketProperty = ObjectProperty[Boolean](this, "skimmerBasket", skimmerBasket)
  val pumpBasketProperty = ObjectProperty[Boolean](this, "pumpBasket", pumpBasket)
  val pumpFilterProperty = ObjectProperty[Boolean](this, "pumpFilter", pumpFilter)
  val vacuumProperty = ObjectProperty[Boolean](this, "vacuum", vacuum)
  val cleanedProperty = ObjectProperty[String](this, "cleaned", cleaned)
  val cleaning = this

object Measurement:
  val totalChlorineRange = Range(1, 5).inclusive
  val freeChlorineRange = Range(1, 5).inclusive
  val combinedChlorineRange = Set(0.0, 0.1, 0.2, 0.3, 0.4, 0.5)
  val phRange = Set(6.2, 6.3, 6.4, 6.5, 6.6, 6.7, 6.8, 6.9, 7.0, 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7, 7.8, 7.9, 8.0, 8.1, 8.2, 8.3, 8.4)
  val calciumHardnessRange = Range(250, 500).inclusive
  val totalAlkalinityRange = Range(80, 120).inclusive
  val cyanuricAcidRange = Range(30, 100).inclusive
  val totalBromineRange = Range(2, 10).inclusive
  val saltRange = Range(2700, 3400).inclusive
  val temperatureRange = Range(50, 100).inclusive

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
                             measured: String = Entity.instant) extends Entity:
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
  val measuredProperty = ObjectProperty[String](this, "measured", measured)
  val measurement = this

enum UnitOfMeasure:
  case gl, l, lb, kg, tablet

object UnitOfMeasure:
  def toList: List[String] = UnitOfMeasure.values.map(uom => uom.toString).toList
  def toPoolList: List[String] = List( UnitOfMeasure.gl.toString, UnitOfMeasure.l.toString )
  def gallonsToLiters(gallons: Double): Double = gallons * 3.785
  def litersToGallons(liters: Double): Double = liters * 0.264
  def poundsToKilograms(pounds: Double): Double = pounds * 0.454
  def kilogramsToPounds(kilograms: Double): Double = kilograms * 2.205

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
                          typeof: String = TypeOfChemical.LiquidChlorine.toString,
                          amount: Double = 1.0, 
                          unit: String = UnitOfMeasure.gl.toString,
                          added: String = Entity.instant) extends Entity:
  val typeofProperty = ObjectProperty[String](this, "typeof", typeof)
  val amountProperty = ObjectProperty[Double](this, "amount", amount)
  val unitProperty = ObjectProperty[String](this, "unit", unit.toString)
  val addedProperty = ObjectProperty[String](this, "added", added)
  val chemical = this