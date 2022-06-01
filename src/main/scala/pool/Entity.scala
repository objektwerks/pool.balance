package pool

import java.time.{LocalDate, LocalTime}
import java.time.format.DateTimeFormatter

enum uom:
  case gl, kg, g, l, ml, lbs, oz

object uom:
  def list: Array[String] = uom.values.map(u => u.toString)

sealed trait Entity:
  val id: Long

  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  def newTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

final case class Pool(id: Long = 0, name: String, built: LocalDate, volume: Int) extends Entity

enum typeofMeasurement:
  case freeChlorine, combinedChlorine, totalChlorine, pH, calciumHardness, totalAlkalinity, cyanuricAcid, totalBromine, temperature

final case class Measurement(id: Long = 0,
                             poolId: Long,
                             typeof: typeofMeasurement,
                             dateMeasured: LocalDate = LocalDate.now,
                             timeMeasured: LocalTime = LocalTime.now,
                             measurement: Double ) extends Entity:
  given measurementOrdering: Ordering[Measurement] = Ordering.by(m => (m.dateMeasured.toEpochDay, m.timeMeasured.toSecondOfDay))


enum typeofChemical:
  case liquidChlorine, trichlor, dichlor, calciumHypochlorite, stabilizer, algaecide

final case class Chemical(id: Long = 0,
                                poolId: Long,
                                typeof: typeofChemical,
                                dateAdded: LocalDate = LocalDate.now,
                                timeAdded: LocalTime = LocalTime.now,
                                amount: Double, 
                                unit: uom) extends Entity:
  given chemicalOrdering: Ordering[Chemical] = Ordering.by(c => (c.dateAdded.toEpochDay, c.timeAdded.toSecondOfDay))