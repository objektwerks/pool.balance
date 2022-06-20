package pool

import com.typesafe.scalalogging.LazyLogging

import scala.util.Try
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.{LongProperty, ObjectProperty}

import Entity.given

final class Model(context: Context) extends LazyLogging:
  private val store = context.store

  val observableErrors = ObservableBuffer[String]()

  val observablePools = ObservableBuffer[Pool]()
  val observableCleanings = ObservableBuffer[Cleaning]()
  val observableMeasurements = ObservableBuffer[Measurement]()
  val observableChemicals = ObservableBuffer[Chemical]()

  val selectedPoolId = ObjectProperty[Long](0)
  val selectedCleaningId = ObjectProperty[Long](0)
  val selectedMeasurementId = ObjectProperty[Long](0)
  val selectedChemicalId = ObjectProperty[Long](0)

  val currentTotalChlorine = ObjectProperty[Int](0)
  val averageTotalChlorine = ObjectProperty[Int](0)

  val currentFreeChlorine = ObjectProperty[Int](0)
  val averageFreeChlorine = ObjectProperty[Int](0)

  val currentCombinedChlorine = ObjectProperty[Double](0)
  val averageCombinedChlorine = ObjectProperty[Double](0)

  val currentPh = ObjectProperty[Double](0)
  val averagePh = ObjectProperty[Double](0)

  val currentCalciumHardness = ObjectProperty[Int](0)
  val averageCalciumHardness = ObjectProperty[Int](0)

  val currentTotalAlkalinity = ObjectProperty[Int](0)
  val averageTotalAlkalinity = ObjectProperty[Int](0)

  val currentCyanuricAcid = ObjectProperty[Int](0)
  val averageCyanuricAcid = ObjectProperty[Int](0)

  val currentTotalBromine = ObjectProperty[Int](0)
  val averageTotalBromine = ObjectProperty[Int](0)

  selectedPoolId.onChange { (_, _, newPoolId) =>
    cleanings(newPoolId)
    measurements(newPoolId)
    chemicals(newPoolId)
    dashboard(newPoolId)
  }

  pools()

  private def pools(): Unit =
    Try {
      observablePools ++= store.pools()
    }.recover { case error: Throwable =>
      val message = s"Loading pools data failed: ${error.getMessage}"
      onError(error, message) 
    }

  private def cleanings(poolId: Long): Unit =
    Try {
      observableCleanings.clear()
      observableCleanings ++= store.cleanings(poolId)
    }.recover { case error: Throwable =>
      val message = s"Loading cleanings data failed: ${error.getMessage}"
      onError(error, message) 
    }

  private def measurements(poolId: Long): Unit =
    Try {
      observableMeasurements.clear()
      observableMeasurements ++= store.measurements(poolId) 
    }.recover { case error: Throwable =>
      val message = s"Loading measurements data failed: ${error.getMessage}"
      onError(error, message) 
    }

  private def chemicals(poolId: Long): Unit =
    Try {
      observableChemicals.clear()
      observableChemicals ++= store.chemicals(poolId) 
    }.recover { case error: Throwable =>
      val message = s"Loading chemicals data failed: ${error.getMessage}"
      onError(error, message) 
    }

  private def dashboard(poolId: Long): Unit =
    Try {
      val measurements = store.measurements(poolId)
      measurements.headOption.foreach { measurement =>
        currentTotalChlorine.value = measurement.totalChlorine
        currentFreeChlorine.value = measurement.freeChlorine
        currentCombinedChlorine.value = measurement.combinedChlorine
        currentPh.value = measurement.ph
        currentCalciumHardness.value = measurement.calciumHardness
        currentTotalAlkalinity.value = measurement.totalAlkalinity
        currentCyanuricAcid.value = measurement.cyanuricAcid
        currentTotalBromine.value = measurement.totalBromine
      }
      averageTotalChlorine.value = measurements.map(_.totalChlorine).sum / measurements.length
      averageFreeChlorine.value = measurements.map(_.freeChlorine).sum / measurements.length
      averageCombinedChlorine.value = measurements.map(_.combinedChlorine).sum / measurements.length
      averagePh.value = measurements.map(_.ph).sum / measurements.length
      averageCalciumHardness.value = measurements.map(_.calciumHardness).sum / measurements.length
      averageTotalAlkalinity.value = measurements.map(_.totalAlkalinity).sum / measurements.length
      averageCyanuricAcid.value = measurements.map(_.cyanuricAcid).sum / measurements.length
      averageTotalBromine.value = measurements.map(_.totalBromine).sum / measurements.length
    }.recover { case error: Throwable =>
      val message = s"Loading dashboard data failed: ${error.getMessage}"
      onError(error, message) 
    }

  private def onError(error: Throwable, message: String) =
    observableErrors += message
    logger.error(message, error)

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