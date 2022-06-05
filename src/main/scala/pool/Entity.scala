package pool

import java.time.{LocalDate, LocalTime}
import java.time.format.DateTimeFormatter

enum unitOfMeasure:
  case gl, kg, g, l, ml, lbs, oz

object unitOfMeasure:
  def list: Array[String] = unitOfMeasure.values.map(u => u.toString)

sealed trait Entity:
  val id: Long

object Entity:
  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  def newTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
  def format(localDate: LocalDate): LocalDate = LocalDate.parse( localDate.format(newDateFormatter) )
  def format(localTime: LocalTime): LocalTime = LocalTime.parse( localTime.format(newTimeFormatter) )

final case class Pool(id: Long = 0,
                      name: String, 
                      built: LocalDate, 
                      volume: Int,
                      unit: unitOfMeasure) extends Entity:
  given poolOrdering: Ordering[Pool] = Ordering.by(p => (p.name))

final case class Cleaning(id: Long = 0,
                          poolId: Long,
                          brush: Boolean = false,
                          net: Boolean = false,
                          skimmerBasket: Boolean = false,
                          pumpBasket: Boolean = false,
                          pumpFilter: Boolean = false,
                          vacuum: Boolean = false,
                          dateCleaned: LocalDate = Entity.format(LocalDate.now)) extends Entity:
  given cleaningOrdering: Ordering[Cleaning] = Ordering.by(c => (c.dateCleaned.toEpochDay))


enum typeOfMeasurement:
  case freeChlorine, combinedChlorine, totalChlorine, pH, calciumHardness, totalAlkalinity, cyanuricAcid, totalBromine, temperature

object typeOfMeasurement:
  def list: Array[String] = typeOfMeasurement.values.map(t => t.toString)

final case class Measurement(id: Long = 0,
                             poolId: Long,
                             typeof: typeOfMeasurement,
                             dateMeasured: LocalDate = Entity.format(LocalDate.now),
                             timeMeasured: LocalTime = Entity.format(LocalTime.now),
                             measurement: Double ) extends Entity:
  given measurementOrdering: Ordering[Measurement] = Ordering.by(m => (m.dateMeasured.toEpochDay, m.timeMeasured.toSecondOfDay))


enum typeOfChemical:
  case liquidChlorine, trichlor, dichlor, calciumHypochlorite, stabilizer, algaecide

object typeOfChemical:
  def list: Array[String] = typeOfChemical.values.map(t => t.toString)

final case class Chemical(id: Long = 0,
                          poolId: Long,
                          typeof: typeOfChemical,
                          dateAdded: LocalDate = Entity.format(LocalDate.now),
                          timeAdded: LocalTime = Entity.format(LocalTime.now),
                          amount: Double, 
                          unit: unitOfMeasure) extends Entity:
  given chemicalOrdering: Ordering[Chemical] = Ordering.by(c => (c.dateAdded.toEpochDay, c.timeAdded.toSecondOfDay))