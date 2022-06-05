package pool

import scala.util.Try
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.LongProperty

final class Model(context: Context):
  private val store = context.store

  private val observablePools = ObservableBuffer[Pool]()
  private val observableCleanings = ObservableBuffer[Cleaning]()
  private val observableMeasurements = ObservableBuffer[Measurement]()
  private val observableChemicals = ObservableBuffer[Chemical]()

  val selectedPoolId = LongProperty(0)

  def pools(): Either[Throwable, ObservableBuffer[Pool]] =
    Try {
      observablePools.clear()
      observablePools ++= store.pools()
    }.toEither
  def add(pool: Pool): Either[Throwable, Unit] =
    Try {
      val newPool = store.add(pool)
      observablePools += newPool
      selectedPoolId.value = newPool.id
    }.toEither
  def update(pool: Pool):Either[Throwable, Unit] = Try( store.update(pool) ).toEither

  def cleanings(): Either[Throwable, ObservableBuffer[Cleaning]] =
    Try {
      observableCleanings.clear()
      observableCleanings ++= store.cleanings()
    }.toEither
  def add(cleaning: Cleaning): Either[Throwable, Cleaning] = Try( store.add(cleaning) ).toEither
  def update(cleaning: Cleaning): Either[Throwable, Unit] = Try( store.update(cleaning) ).toEither

  def measurements(typeof: typeOfMeasurement): Either[Throwable, ObservableBuffer[Measurement]] =
    Try {
      observableMeasurements.clear()
      observableMeasurements ++= store.measurements(typeof) 
    }.toEither
  def add(measurement: Measurement): Either[Throwable, Measurement] = Try( store.add(measurement) ).toEither
  def update(measurement: Measurement): Either[Throwable, Unit] = Try( store.update(measurement) ).toEither

  def chemicals(typeof: typeOfChemical): Either[Throwable, ObservableBuffer[Chemical]] =
    Try {
      observableChemicals.clear()
      observableChemicals ++= store.chemicals(typeof) 
    }.toEither
  def add(chemical: Chemical): Either[Throwable, Chemical] = Try( store.add(chemical) ).toEither
  def update(chemical: Chemical): Either[Throwable, Unit] = Try( store.update(chemical) ).toEither