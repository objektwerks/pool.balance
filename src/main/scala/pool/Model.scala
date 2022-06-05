package pool

import scala.util.Try
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.LongProperty

import Entity.given

final class Model(context: Context):
  private val store = context.store

  private val observablePools = ObservableBuffer[Pool]()
  private val observableCleanings = ObservableBuffer[Cleaning]()
  private val observableMeasurements = ObservableBuffer[Measurement]()
  private val observableChemicals = ObservableBuffer[Chemical]()

  val selectedPoolId = LongProperty(0)
  val selectedCleaningId = LongProperty(0)
  val selectedMeasurementId = LongProperty(0)
  val selectedChemicalId = LongProperty(0)

  def pools(): Either[Throwable, ObservableBuffer[Pool]] =
    Try {
      observablePools.clear()
      observableCleanings.clear()
      observableMeasurements.clear()
      observableChemicals.clear()
      observablePools ++= store.pools()
    }.toEither

  def add(pool: Pool): Either[Throwable, Pool] =
    Try {
      val newPool = store.add(pool)
      observablePools += newPool
      observablePools.sort()
      selectedPoolId.value = newPool.id
      newPool
    }.toEither

  def update(selectedIndex: Int, pool: Pool): Either[Throwable, Unit] =
    Try {
      store.update(pool)
      observablePools.update(selectedIndex, pool)
      observablePools.sort()
      selectedPoolId.value = pool.id
    }.toEither

  def cleanings(poolId: Long): Either[Throwable, ObservableBuffer[Cleaning]] =
    Try {
      observableCleanings.clear()
      observableCleanings ++= store.cleanings(poolId)
    }.toEither

  def add(cleaning: Cleaning): Either[Throwable, Cleaning] =
    Try {
      val newCleaning = store.add(cleaning)
      observableCleanings += newCleaning
      observableCleanings.sort()
      selectedCleaningId.value = newCleaning.id
      newCleaning
    }.toEither

  def update(selectedIndex: Int, cleaning: Cleaning): Either[Throwable, Unit] =
    Try {
      store.update(cleaning)
      observableCleanings.update(selectedIndex, cleaning)
      observableCleanings.sort()
      selectedCleaningId.value = cleaning.id
    }.toEither

  def measurements(poolId: Long, typeof: typeOfMeasurement): Either[Throwable, ObservableBuffer[Measurement]] =
    Try {
      observableMeasurements.clear()
      observableMeasurements ++= store.measurements(poolId, typeof) 
    }.toEither
  
  def add(measurement: Measurement): Either[Throwable, Measurement] =
    Try {
      val newMeasurement = store.add(measurement)
      observableMeasurements += newMeasurement
      observableMeasurements.sort()
      selectedMeasurementId.value = newMeasurement.id
      newMeasurement
    }.toEither

  def update(selectedIndex: Int, measurement: Measurement): Either[Throwable, Unit] =
    Try {
      store.update(measurement)
      observableMeasurements.update(selectedIndex, measurement)
      observableMeasurements.sort()
      selectedMeasurementId.value = measurement.id
    }.toEither

  def chemicals(poolId: Long, typeof: typeOfChemical): Either[Throwable, ObservableBuffer[Chemical]] =
    Try {
      observableChemicals.clear()
      observableChemicals ++= store.chemicals(poolId, typeof) 
    }.toEither
  
  def add(chemical: Chemical): Either[Throwable, Chemical] =
    Try {
      val newChemical = store.add(chemical)
      observableChemicals += newChemical
      observableChemicals.sort()
      selectedChemicalId.value = newChemical.id      
      newChemical
    }.toEither

  def update(selectedIndex: Int, chemical: Chemical): Either[Throwable, Unit] =
    Try {
      store.update(chemical)
      observableChemicals.update(selectedIndex, chemical)
      observableChemicals.sort()
      selectedChemicalId.value = chemical.id
    }.toEither