package pool

import java.time.Instant
import java.time.format.DateTimeFormatter

sealed trait Event

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