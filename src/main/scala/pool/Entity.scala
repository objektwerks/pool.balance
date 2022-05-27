package pool

import java.time.{LocalDate, LocalTime}

trait Entity:
  val id: Int
  val date: LocalDate
  val time: LocalTime

