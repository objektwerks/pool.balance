package pool

import java.time.{LocalDate, LocalTime}
import java.time.format.DateTimeFormatter

sealed trait Entity:
  val id: Long

final case class Pool(id: Long = 0, name: String, built: LocalDate, volume: Int = 10000) extends Entity

sealed trait Measurement extends Entity:
  val id: Long = 0
  val measurement: Double
  val dateMeasured: LocalDate = LocalDate.now
  val timeMeasured: LocalTime = LocalTime.now

  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
  def newTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm")

  given measurementOrdering: Ordering[Measurement] = Ordering.by(m => (m.dateMeasured.toEpochDay, m.timeMeasured.toSecondOfDay))

final case class FreeChlorine(measurement: Double) extends Measurement

final case class CombinedChlorine(measurement: Double) extends Measurement

final case class TotalChlorine(measurement: Double) extends Measurement

final case class pH(measurement: Double) extends Measurement

final case class CalciumHardness(measurement: Double) extends Measurement

final case class TotalAlkalinity(measurement: Double) extends Measurement

final case class CyanuricAcid(measurement: Double) extends Measurement

final case class TotalBromine(measurement: Double) extends Measurement

final case class Temperature(measurement: Double) extends Measurement