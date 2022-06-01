package pool

import java.time.{LocalDate, LocalTime}
import java.time.format.DateTimeFormatter

enum UM:
  case gallons, grams, kilos, liters, lbs, ml, oz

sealed trait Entity:
  val id: Long

  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  def newTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

final case class Pool(id: Long = 0, name: String, built: LocalDate, volume: Int) extends Entity

sealed trait Measurement extends Entity:
  val id: Long = 0
  val poolId: Long
  val dateMeasured: LocalDate = LocalDate.now
  val timeMeasured: LocalTime = LocalTime.now
  val measurement: Double

  given measurementOrdering: Ordering[Measurement] = Ordering.by(m => (m.dateMeasured.toEpochDay, m.timeMeasured.toSecondOfDay))

final case class FreeChlorine(poolId: Long, measurement: Double) extends Measurement

final case class CombinedChlorine(poolId: Long, measurement: Double) extends Measurement

final case class TotalChlorine(poolId: Long, measurement: Double) extends Measurement

final case class pH(poolId: Long, measurement: Double) extends Measurement

final case class CalciumHardness(poolId: Long, measurement: Double) extends Measurement

final case class TotalAlkalinity(poolId: Long, measurement: Double) extends Measurement

final case class CyanuricAcid(poolId: Long, measurement: Double) extends Measurement

final case class TotalBromine(poolId: Long, measurement: Double) extends Measurement

final case class Temperature(poolId: Long, measurement: Double) extends Measurement

sealed trait Chemical extends Entity:
  val id: Long = 0
  val poolId: Long
  val dateAdded: LocalDate = LocalDate.now
  val timeAdded: LocalTime = LocalTime.now
  val amount: Double
  val unit: String

  given chemicalOrdering: Ordering[Chemical] = Ordering.by(c => (c.dateAdded.toEpochDay, c.timeAdded.toSecondOfDay))

final case class LiquidChlorine(poolId: Long, amount: Double, unit: String) extends Chemical

final case class Trichlor(poolId: Long, amount: Double, unit: String) extends Chemical

final case class Dichlor(poolId: Long, amount: Double, unit: String) extends Chemical

final case class CalciumHypochlorite(poolId: Long, amount: Double, unit: String) extends Chemical

final case class Stabilizer(poolId: Long, amount: Double, unit: String) extends Chemical

final case class Algaecide(poolId: Long, amount: Double, unit: String) extends Chemical