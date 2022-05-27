package pool

import java.time.{LocalDate, LocalTime}
import java.time.format.DateTimeFormatter

sealed trait Entity:
  val id: Int = 0

final case class Pool(volume: Int = 10000) extends Entity

sealed trait Measurement extends Entity:
  val value: Double
  val dateCreated: LocalDate = LocalDate.now
  val timeCreated: LocalTime = LocalTime.now

  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
  def newTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm")
  
final case class FreeChlorine(value: Double) extends Measurement

final case class CombinedChlorine(value: Double) extends Measurement

final case class TotalChlorine(value: Double) extends Measurement

final case class pH(value: Double) extends Measurement

final case class CalciumHardness(value: Double) extends Measurement

final case class TotalAlkalinity(value: Double) extends Measurement

final case class CyanuricAcid(value: Double) extends Measurement

final case class TotalBromine(value: Double) extends Measurement

final case class Temperature(value: Double) extends Measurement