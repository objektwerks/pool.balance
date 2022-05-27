package pool

import java.time.{LocalDate, LocalTime}

sealed trait Entity:
  val id: Int
  val date: LocalDate
  val time: LocalTime

final case class Pool(id: Int, date: LocalDate, time: LocalTime, volume: Int) extends Entity
