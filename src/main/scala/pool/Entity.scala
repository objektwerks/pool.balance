package pool

import java.time.{LocalDate, LocalTime, LocalDateTime}
import java.time.format.DateTimeFormatter

import math.BigDecimal.double2bigDecimal

import scalafx.beans.property.ObjectProperty

enum UnitOfMeasure:
  case gl, kg, g, l, ml, lbs, oz

sealed trait Entity:
  val id: Long

object Entity:
  def newDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  def newTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
  def format(localDateTime: LocalDateTime): LocalDateTime = LocalDateTime.parse( localDateTime.format(newDateTimeFormatter) )
  def format(localDate: LocalDate): LocalDate = LocalDate.parse( localDate.format(newDateFormatter) )
  def format(localTime: LocalTime): LocalTime = LocalTime.parse( localTime.format(newTimeFormatter) )

  def applyLocalDate(localDate: LocalDate, localDateTime: LocalDateTime): LocalDateTime =
    localDateTime
      .withYear(localDate.getYear)
      .withMonth(localDate.getMonthValue)
      .withDayOfMonth(localDate.getDayOfMonth)

  given poolOrdering: Ordering[Pool] = Ordering.by[Pool, String](p => p.name).reverse
  given cleaningOrdering: Ordering[Cleaning] = Ordering.by[Cleaning, Long](c => c.cleaned.toLocalDate.toEpochDay).reverse
  given measurementOrdering: Ordering[Measurement] = Ordering.by[Measurement, Long](m => m.measured.toLocalDate.toEpochDay).reverse
  given chemicalOrdering: Ordering[Chemical] = Ordering.by[Chemical, Long](c => c.added.toLocalDate.toEpochDay).reverse

  def isNotInt(text: String): Boolean = !text.matches("\\d+")
  def isNotDouble(text: String): Boolean = !text.matches("\\d{0,7}([\\.]\\d{0,4})?")

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
                          brush: Boolean = false,
                          net: Boolean = false,
                          skimmerBasket: Boolean = false,
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
  val cleanedProperty = ObjectProperty[String](this, "cleaned", Entity.format(cleaned).toString)
  val cleaning = this

enum TypeOfMeasurement:
  case totalChlorine, freeChlorine, combinedChlorine, ph, calciumHardness, totalAlkalinity, cyanuricAcid, totalBromine, temperature

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
  val temperatureProperty = ObjectProperty[Int](this, "temperature", temperature)
  val measuredProperty = ObjectProperty[String](this, "measured", Entity.format(measured).toString)
  val measurement = this

enum TypeOfChemical:
  case liquidChlorine, trichlor, dichlor, calciumHypochlorite, stabilizer, algaecide, muriaticAcid

final case class Chemical(id: Long = 0,
                          poolId: Long,
                          typeof: TypeOfChemical = TypeOfChemical.liquidChlorine,
                          amount: Double = 1.0, 
                          unit: UnitOfMeasure = UnitOfMeasure.gl,
                          added: LocalDateTime = LocalDateTime.now) extends Entity:
  val typeofProperty = ObjectProperty[String](this, "typeof", typeof.toString)
  val amountProperty = ObjectProperty[Double](this, "amount", amount)
  val unitProperty = ObjectProperty[String](this, "unit", unit.toString)
  val addedProperty = ObjectProperty[String](this, "added", Entity.format(added).toString)
  val chemical = this