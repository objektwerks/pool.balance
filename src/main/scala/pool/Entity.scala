package pool

import java.time.{LocalDate, LocalTime}
import java.time.format.DateTimeFormatter

sealed trait Entity:
  val id: Int = 0
  val dateCreated: LocalDate = LocalDate.now
  val timeCreated: LocalTime = LocalTime.now

  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

  def newTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm")

final case class Pool(volume: Int = 10000) extends Entity

sealed trait Measurement extends Entity:
  val value: Double = 0.0

final case class FreeChlorine() extends Measurement

final case class CombinedChlorine() extends Measurement

final case class TotalChlorine() extends Measurement

final case class pH() extends Measurement

final case class CalciumHardness() extends Measurement

final case class TotalAlkalinity() extends Measurement

final case class CyanuricAcid() extends Measurement

final case class TotalBromine() extends Measurement

final case class Temperature() extends Measurement