package pool

import scala.util.Try
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.{LongProperty, ObjectProperty}

import Entity.given

final class Model(context: Context):
  private val store = context.store

  val observablePools = ObservableBuffer[Pool]()
  val observableCleanings = ObservableBuffer[Cleaning]()
  val observableMeasurements = ObservableBuffer[Measurement]()
  val observableChemicals = ObservableBuffer[Chemical]()

  val selectedPoolId = LongProperty(0)
  val selectedCleaningId = LongProperty(0)
  val selectedMeasurementId = LongProperty(0)
  val selectedChemicalId = LongProperty(0)

  val currentTotalChlorine = ObjectProperty[Int](0)
  val averageTotalChlorine = ObjectProperty[Int](0)

  val currentFreeChlorine = ObjectProperty[Int](0)
  val averageFreeChlorine = ObjectProperty[Int](0)

  val currentCombinedChlorine = ObjectProperty[Int](0)
  val averageCombinedChlorine = ObjectProperty[Int](0)

  val currentPh = ObjectProperty[Int](0)
  val averagePh = ObjectProperty[Int](0)

  val currentCalciumHardness = ObjectProperty[Int](0)
  val averageCalciumHardness = ObjectProperty[Int](0)

  val currentTotalAlkalinity = ObjectProperty[Int](0)
  val averageTotalAlkalinity = ObjectProperty[Int](0)

  val currentCyanuricAcid = ObjectProperty[Int](0)
  val averageCyanuricAcid = ObjectProperty[Int](0)

  val currentTotalBromine = ObjectProperty[Int](0)
  val averageTotalBromine = ObjectProperty[Int](0)

  selectedPoolId.onChange { (_, _, newPoolId) =>
    val poolId = newPoolId.asInstanceOf[Long]
    cleanings(poolId)
    measurements(poolId)
    chemicals(poolId)
    // dashboard
  }

  private def pools(): Either[Throwable, ObservableBuffer[Pool]] =
    Try {
      observablePools ++= store.pools()
    }.toEither

  private def cleanings(poolId: Long): Either[Throwable, Unit] =
    Try {
      observableCleanings.clear()
      observableCleanings ++= store.cleanings(poolId)
      ()
    }.toEither

  private def measurements(poolId: Long): Either[Throwable, ObservableBuffer[Measurement]] =
    Try {
      observableMeasurements.clear()
      observableMeasurements ++= store.measurements(poolId) 
    }.toEither

  private def chemicals(poolId: Long): Either[Throwable, ObservableBuffer[Chemical]] =
    Try {
      observableChemicals.clear()
      observableChemicals ++= store.chemicals(poolId) 
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