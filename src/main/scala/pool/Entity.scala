package pool

import java.time.{LocalDate, LocalTime}
import java.time.format.DateTimeFormatter

sealed trait Entity:
  val id: Long

final case class Pool(id: Long = 0, name: String, built: LocalDate, volume: Int) extends Entity

sealed trait Measurement extends Entity:
  val id: Long = 0
  val poolId: Long
  val measurement: Double
  val dateMeasured: LocalDate = LocalDate.now
  val timeMeasured: LocalTime = LocalTime.now

  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
  def newTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm")

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

final case class Chemical(id: Long = 0,
                          poolId: Long = 0,
                          dateAdded: LocalDate = LocalDate.now,
                          timeAdded: LocalTime = LocalTime.now,
                          chemical: String = "chlorine",
                          amount: Double = 1.0,
                          unit: String = "gallon") extends Entity