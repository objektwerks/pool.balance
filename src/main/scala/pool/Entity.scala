package pool

import java.time.{LocalDate, LocalTime}
import java.time.format.DateTimeFormatter

enum uom:
  case gl, kg, g, l, ml, lbs, oz

object uom:
  def list: Array[String] = uom.values.map(u => u.toString)

sealed trait Entity:
  val id: Long

  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  def newTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

final case class Pool(id: Long = 0, name: String, built: LocalDate, volume: Int) extends Entity

sealed trait Measurement extends Entity:
  val id: Long
  val poolId: Long
  val dateMeasured: LocalDate
  val timeMeasured: LocalTime
  val measurement: Double

  given measurementOrdering: Ordering[Measurement] = Ordering.by(m => (m.dateMeasured.toEpochDay, m.timeMeasured.toSecondOfDay))

final case class FreeChlorine(id: Long = 0,
                              poolId: Long,
                              dateMeasured: LocalDate,
                              timeMeasured: LocalTime,
                              measurement: Double) extends Measurement

final case class CombinedChlorine(id: Long,
                                  poolId: Long,
                                  dateMeasured: LocalDate,
                                  timeMeasured: LocalTime,
                                  measurement: Double) extends Measurement

final case class TotalChlorine(id: Long,
                               poolId: Long,
                               dateMeasured: LocalDate,
                               timeMeasured: LocalTime,
                               measurement: Double) extends Measurement
final case class pH(id: Long = 0,
                    poolId: Long,
                    dateMeasured: LocalDate,
                    timeMeasured: LocalTime,
                    measurement: Double) extends Measurement

final case class CalciumHardness(id: Long = 0,
                                 poolId: Long,
                                 dateMeasured: LocalDate,
                                 timeMeasured: LocalTime,
                                 measurement: Double) extends Measurement

final case class TotalAlkalinity(id: Long = 0,
                                 poolId: Long,
                                 dateMeasured: LocalDate,
                                 timeMeasured: LocalTime,
                                 measurement: Double) extends Measurement

final case class CyanuricAcid(id: Long = 0,
                              poolId: Long,
                              dateMeasured: LocalDate,
                              timeMeasured: LocalTime,
                              measurement: Double) extends Measurement

final case class TotalBromine(id: Long = 0,
                              poolId: Long,
                              dateMeasured: LocalDate,
                              timeMeasured: LocalTime,
                              measurement: Double) extends Measurement

final case class Temperature(id: Long = 0,
                             poolId: Long,
                             dateMeasured: LocalDate,
                             timeMeasured: LocalTime,
                             measurement: Double) extends Measurement

sealed trait Chemical extends Entity:
  val id: Long = 0
  val poolId: Long
  val dateAdded: LocalDate = LocalDate.now
  val timeAdded: LocalTime = LocalTime.now
  val amount: Double
  val unit: uom

  given chemicalOrdering: Ordering[Chemical] = Ordering.by(c => (c.dateAdded.toEpochDay, c.timeAdded.toSecondOfDay))

final case class LiquidChlorine(poolId: Long, amount: Double, unit: uom) extends Chemical

final case class Trichlor(poolId: Long, amount: Double, unit: uom) extends Chemical

final case class Dichlor(poolId: Long, amount: Double, unit: uom) extends Chemical

final case class CalciumHypochlorite(poolId: Long, amount: Double, unit: uom) extends Chemical

final case class Stabilizer(poolId: Long, amount: Double, unit: uom) extends Chemical

final case class Algaecide(poolId: Long, amount: Double, unit: uom) extends Chemical