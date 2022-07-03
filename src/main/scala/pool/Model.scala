package pool

import com.typesafe.scalalogging.LazyLogging

import java.text.NumberFormat

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

import scalafx.application.Platform
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

  val currentSalt = ObjectProperty[Int](0)
  val averageSalt = ObjectProperty[Int](0)

  selectedPoolId.onChange { (_, oldPoolId, newPoolId) =>
    require(Platform.isFxApplicationThread, "selected pool id onchange should be in fx thread.")
    logger.info(s"selected oool id onchange event: $oldPoolId -> $newPoolId")
    cleanings(newPoolId)
    measurements(newPoolId)
    chemicals(newPoolId)
  }

  observableMeasurements.onChange{ (_, _) =>
    require(!Platform.isFxApplicationThread, "via measurements, observable measurements onchange should not be in fx thread.")
    logger.info(s"observable measurements onchange event.")
    Platform.runLater( dashboard() )
  }

  pools()

  private def pools(): Unit =
    Future {
      require(!Platform.isFxApplicationThread, "pools should not be in fx thread.")
      observablePools ++= store.pools()
    }.recover { case error: Throwable => onError(error, s"Loading pools data failed: ${error.getMessage}") }

  private def cleanings(poolId: Long): Unit =
    Future {
      require(!Platform.isFxApplicationThread, "cleanings should not be in fx thread.")
      observableCleanings.clear()
      observableCleanings ++= store.cleanings(poolId)
    }.recover { case error: Throwable => onError(error, s"Loading cleanings data failed: ${error.getMessage}") }

  private def measurements(poolId: Long): Unit =
    Future {
      require(!Platform.isFxApplicationThread, "measurements should not be in fx thread.")
      observableMeasurements.clear()
      observableMeasurements ++= store.measurements(poolId) 
    }.recover { case error: Throwable => onError(error, s"Loading measurements data failed: ${error.getMessage}") }

  private def chemicals(poolId: Long): Unit =
    Future {
      require(!Platform.isFxApplicationThread, "chemicals should not be in fx thread.")
      observableChemicals.clear()
      observableChemicals ++= store.chemicals(poolId) 
    }.recover { case error: Throwable => onError(error, s"Loading chemicals data failed: ${error.getMessage}") }

  private def dashboard(): Unit =
    require(Platform.isFxApplicationThread, "dashboard should be in fx thread.")
    val numberFormat = NumberFormat.getNumberInstance()
    numberFormat.setMaximumFractionDigits(1)
    val measurements = observableMeasurements
    measurements.headOption.foreach { measurement =>
      currentTotalChlorine.value = measurement.totalChlorine
      currentFreeChlorine.value = measurement.freeChlorine
      currentCombinedChlorine.value = numberFormat.format( measurement.combinedChlorine ).toDouble
      currentPh.value = numberFormat.format( measurement.ph ).toDouble
      currentCalciumHardness.value = measurement.calciumHardness
      currentTotalAlkalinity.value = measurement.totalAlkalinity
      currentCyanuricAcid.value = measurement.cyanuricAcid
      currentTotalBromine.value = measurement.totalBromine
      currentSalt.value = measurement.salt

      val count = measurements.length
      averageTotalChlorine.value = measurements.map(_.totalChlorine).sum / count
      averageFreeChlorine.value = measurements.map(_.freeChlorine).sum / count
      averageCombinedChlorine.value = numberFormat.format( measurements.map(_.combinedChlorine).sum / count ).toDouble
      averagePh.value = numberFormat.format( measurements.map(_.ph).sum / count ).toDouble
      averageCalciumHardness.value = measurements.map(_.calciumHardness).sum / count
      averageTotalAlkalinity.value = measurements.map(_.totalAlkalinity).sum / count
      averageCyanuricAcid.value = measurements.map(_.cyanuricAcid).sum / count
      averageTotalBromine.value = measurements.map(_.totalBromine).sum / count
      averageSalt.value = measurements.map(_.salt).sum / count
    }

  def onError(message: String): Unit =
    require(Platform.isFxApplicationThread, "onerror message should be in fx thread.")
    observableErrors += message
    logger.error(message)

  def onError(error: Throwable, message: String): Unit =
    require(Platform.isFxApplicationThread, "onerror error, message should be in fx thread.")
    observableErrors += message
    logger.error(message, error)

  def add(pool: Pool): Future[Pool] =
    Future {
      require(!Platform.isFxApplicationThread, "add pool should not be in fx thread.")
      val newPool = store.add(pool)
      observablePools += newPool
      observablePools.sort()
      selectedPoolId.value = newPool.id
      newPool
    }

  def update(selectedIndex: Int, pool: Pool): Future[Unit] =
    Future {
      require(!Platform.isFxApplicationThread, "update pool should not be in fx thread.")
      store.update(pool)
      observablePools.update(selectedIndex, pool)
      observablePools.sort()
      selectedPoolId.value = pool.id
    }

  def add(cleaning: Cleaning): Future[Cleaning] =
    Future {
      require(!Platform.isFxApplicationThread, "add cleaning should not be in fx thread.")
      val newCleaning = store.add(cleaning)
      observableCleanings += newCleaning
      observableCleanings.sort()
      selectedCleaningId.value = newCleaning.id
      newCleaning
    }

  def update(selectedIndex: Int, cleaning: Cleaning): Future[Unit] =
    Future {
      require(!Platform.isFxApplicationThread, "update cleaning should not be in fx thread.")
      store.update(cleaning)
      observableCleanings.update(selectedIndex, cleaning)
      observableCleanings.sort()
      selectedCleaningId.value = cleaning.id
    }
  
  def add(measurement: Measurement): Future[Measurement] =
    Future {
      require(!Platform.isFxApplicationThread, "add measurement should not be in fx thread.")
      val newMeasurement = store.add(measurement)
      observableMeasurements += newMeasurement
      observableMeasurements.sort()
      selectedMeasurementId.value = newMeasurement.id
      newMeasurement
    }

  def update(selectedIndex: Int, measurement: Measurement): Future[Unit] =
    Future {
      require(!Platform.isFxApplicationThread, "update measurement should not be in fx thread.")
      store.update(measurement)
      observableMeasurements.update(selectedIndex, measurement)
      observableMeasurements.sort()
      selectedMeasurementId.value = measurement.id
    }
  
  def add(chemical: Chemical): Future[Chemical] =
    Future {
      require(!Platform.isFxApplicationThread, "add chemical should not be in fx thread.")
      val newChemical = store.add(chemical)
      observableChemicals += newChemical
      observableChemicals.sort()
      selectedChemicalId.value = newChemical.id      
      newChemical
    }

  def update(selectedIndex: Int, chemical: Chemical): Future[Unit] =
    Future {
      require(!Platform.isFxApplicationThread, "update chemical should not be in fx thread.")
      store.update(chemical)
      observableChemicals.update(selectedIndex, chemical)
      observableChemicals.sort()
      selectedChemicalId.value = chemical.id
    }