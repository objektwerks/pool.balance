package pool

import java.time.{Instant, LocalDate}

import scalafx.beans.property.ObjectProperty

sealed trait Event

final case class Authorized(isAuthorized: Boolean) extends Event

final case class Registered(account: Account) extends Event
final case class LoggedIn(account: Account) extends Event

final case class Deactivated(account: Account) extends Event
final case class Reactivated(account: Account) extends Event

final case class PoolsListed(pools: List[Pool]) extends Event
final case class PoolSaved(id: Long) extends Event

final case class CleaningsListed(cleanings: List[Cleaning]) extends Event
final case class CleaningSaved(id: Long) extends Event

final case class MeasurementsListed(measurements: List[Measurement]) extends Event
final case class MeasurementSaved(id: Long) extends Event

final case class ChemicalsListed(chemicals: List[Chemical]) extends Event
final case class ChemicalSaved(id: Long) extends Event

object Fault:
  def apply(message: String, throwable: Throwable): Fault = Fault(s"$message ${throwable.getMessage}")

  given faultOrdering: Ordering[Fault] = Ordering.by[Fault, Long](f => LocalDate.parse(f.occurred).toEpochDay()).reverse

final case class Fault(cause: String, occurred: String = Instant.now.toString) extends Event:
  val causeProperty = ObjectProperty[String](this, "cause", cause)
  val occurredProperty = ObjectProperty[String](this, "occurred", occurred)

final case class FaultAdded() extends Event