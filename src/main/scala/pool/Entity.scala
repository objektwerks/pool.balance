package pool

import java.time.{LocalDate, LocalTime, LocalDateTime}
import java.time.format.DateTimeFormatter

import scalafx.beans.property.{BooleanProperty, IntegerProperty, StringProperty}

enum unitOfMeasure:
  case gl, kg, g, l, ml, lbs, oz

object unitOfMeasure:
  def list: Array[String] = unitOfMeasure.values.map(u => u.toString)

sealed trait Entity:
  val id: Long

object Entity:
  def newDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm")
  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  def newTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
  def format(localDateTime: LocalDateTime): LocalDateTime = LocalDateTime.parse( localDateTime.format(newDateTimeFormatter) )
  def format(localDate: LocalDate): LocalDate = LocalDate.parse( localDate.format(newDateFormatter) )
  def format(localTime: LocalTime): LocalTime = LocalTime.parse( localTime.format(newTimeFormatter) )

  given poolOrdering: Ordering[Pool] = Ordering.by[Pool, Long](p => p.built).reverse
  given cleaningOrdering: Ordering[Cleaning] = Ordering.by[Cleaning, Long](c => c.cleaned.toLocalDate.toEpochDay).reverse
  given measurementOrdering: Ordering[Measurement] = Ordering.by[Measurement, Long](m => m.measured.toLocalDate.toEpochDay).reverse
  given chemicalOrdering: Ordering[Chemical] = Ordering.by[Chemical, Long](c => c.added.toLocalDate.toEpochDay).reverse

final case class Pool(id: Long = 0,
                      name: String, 
                      built: Int, 
                      volume: Int,
                      unit: unitOfMeasure) extends Entity:
  val nameProperty = new StringProperty(this, "name", name)
  val builtProperty = new IntegerProperty(this, "built", built)
  val volumeProperty = new IntegerProperty(this, "volume", volume)
  val unitProperty = new StringProperty(this, "unit", unit.toString)
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
  val brushProperty = new BooleanProperty(this, "brush", brush)
  val netProperty = new BooleanProperty(this, "net", net)
  val skimmerBasketProperty = new BooleanProperty(this, "skimmerBasket", skimmerBasket)
  val pumpBasketProperty = new BooleanProperty(this, "pumpBasket", pumpBasket)
  val pumpFilterProperty = new BooleanProperty(this, "pumpFilter", pumpFilter)
  val vacuumProperty = new BooleanProperty(this, "vacuum", vacuum)
  val cleanedProperty = new StringProperty(this, "cleaned", Entity.format(cleaned).toString)
  val cleaning = this

final case class Measurement(id: Long = 0,
                             poolId: Long,
                             freeChlorine: Int = 3,
                             combinedChlorine: Double = 0.0,
                             totalChlorine: Int = 3,
                             ph: Double = 7.4,
                             calciumHardness: Int = 375,
                             totalAlkalinity: Int = 100,
                             cyanuricAcid: Int = 50,
                             totalBromine: Int = 5,
                             temperature: Int = 85,
                             measured: LocalDateTime = LocalDateTime.now) extends Entity

enum typeOfChemical:
  case liquidChlorine, trichlor, dichlor, calciumHypochlorite, stabilizer, algaecide, muriaticAcid

object typeOfChemical:
  def list: Array[String] = typeOfChemical.values.map(t => t.toString)

final case class Chemical(id: Long = 0,
                          poolId: Long,
                          typeof: typeOfChemical,
                          amount: Double, 
                          unit: unitOfMeasure,
                          added: LocalDateTime = LocalDateTime.now) extends Entity