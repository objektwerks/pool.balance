package pool

import java.time.{LocalDate, LocalTime}
import java.time.format.DateTimeFormatter

/*
6. CalciumHardness
7. TotalAlkalinity
8. CyanuricAcid
9. TotalBromine
10. Temperature
*/

sealed trait Entity:
  val id: Int = 0
  val date: LocalDate = LocalDate.now
  val time: LocalTime = LocalTime.now

  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

  def newTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm")

final case class Pool(volume: Int = 10000) extends Entity

final case class FreeChlorine(value: Double) extends Entity

final case class CombinedChlorine(value: Double) extends Entity

final case class TotalChlorine(value: Double) extends Entity

final case class pH(value: Double) extends Entity

final case class CalciumHardness(value: Double) extends Entity