package pool

import java.time.{LocalDate, LocalTime}
import java.time.format.DateTimeFormatter

enum unitOfMeasure:
  case gl, kg, g, l, ml, lbs, oz

object unitOfMeasure:
  def list: Array[String] = unitOfMeasure.values.map(u => u.toString)

sealed trait Entity:
  val id: Long

object Entity:
  def newDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  def format(localDate: LocalDate): LocalDate = LocalDate.parse( localDate.format(newDateFormatter) )

  given poolOrdering: Ordering[Pool] = Ordering.by[Pool, Long](p => p.built.toEpochDay).reverse
  given cleaningOrdering: Ordering[Cleaning] = Ordering.by[Cleaning, Long](c => c.cleaned.toEpochDay).reverse
  given measurementOrdering: Ordering[Measurement] = Ordering.by[Measurement, Long](m => m.measured.toEpochDay).reverse
  given chemicalOrdering: Ordering[Chemical] = Ordering.by[Chemical, Long](c => c.added.toEpochDay).reverse

final case class Pool(id: Long = 0,
                      name: String, 
                      built: LocalDate, 
                      volume: Int,
                      unit: unitOfMeasure) extends Entity

final case class Cleaning(id: Long = 0,
                          poolId: Long,
                          brush: Boolean = false,
                          net: Boolean = false,
                          skimmerBasket: Boolean = false,
                          pumpBasket: Boolean = false,
                          pumpFilter: Boolean = false,
                          vacuum: Boolean = false,
                          cleaned: LocalDate = Entity.format(LocalDate.now)) extends Entity

final case class Measurement(id: Long = 0,
                             poolId: Long,
                             freeChlorine: Int = 3,
                             combinedChlorine: Double = 0.0,
                             totalChlorine: Int = 3,
                             ph: Double = 7.4,
                             calciumHardness: Int = 375,
                             totalAlkalinity: Int = 100,
                             cyanuricAcid: Int = 50,
                             totalBromine: Int = 5,
                             temperature: Int = 85,
                             measured: LocalDate = Entity.format(LocalDate.now)) extends Entity

enum typeOfChemical:
  case liquidChlorine, trichlor, dichlor, calciumHypochlorite, stabilizer, algaecide, muriaticAcid

object typeOfChemical:
  def list: Array[String] = typeOfChemical.values.map(t => t.toString)

final case class Chemical(id: Long = 0,
                          poolId: Long,
                          typeof: typeOfChemical,
                          amount: Double, 
                          unit: unitOfMeasure,
                          added: LocalDate = Entity.format(LocalDate.now)) extends Entity