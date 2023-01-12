package pool

import com.typesafe.scalalogging.LazyLogging

import java.text.NumberFormat
import java.util.concurrent.Executors

import scalafx.Includes.*
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

import scalafx.application.Platform
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.ObjectProperty

import Entity.given
import Measurement.*

final class Model(fetcher: Fetcher) extends LazyLogging:
  given executionContext: ExecutionContext = ExecutionContext.fromExecutor( Executors.newVirtualThreadPerTaskExecutor() )
  val shouldBeInFxThread = (message: String) => require(Platform.isFxApplicationThread, message)
  val shouldNotBeInFxThread = (message: String) => require(!Platform.isFxApplicationThread, message)

  val selectedPoolId = ObjectProperty[Long](0)
  val selectedCleaningId = ObjectProperty[Long](0)
  val selectedMeasurementId = ObjectProperty[Long](0)
  val selectedChemicalId = ObjectProperty[Long](0)

  selectedPoolId.onChange { (_, oldPoolId, newPoolId) =>
    shouldBeInFxThread("selected pool id onchange should be in fx thread.")
    logger.info(s"selected oool id onchange event: $oldPoolId -> $newPoolId")
    cleanings(newPoolId)
    measurements(newPoolId)
    chemicals(newPoolId)
  }

  selectedCleaningId.onChange { (_, oldId, newId) =>
    logger.info(s"*** Model: selected cleaning id onchange event: $oldId -> $newId")
  }

  selectedMeasurementId.onChange { (_, oldId, newId) =>
    logger.info(s"*** Model: selected measurement id onchange event: $oldId -> $newId")
  }

  selectedChemicalId.onChange { (_, oldId, newId) =>
    logger.info(s"*** Model: selected chemical id onchange event: $oldId -> $newId")
  }

  val observableAccount = ObjectProperty[Account](Account.empty)
  val observablePools = ObservableBuffer[Pool]()
  val observableCleanings = ObservableBuffer[Cleaning]()
  val observableMeasurements = ObservableBuffer[Measurement]()
  val observableChemicals = ObservableBuffer[Chemical]()
  val observableErrors = ObservableBuffer[Error]()

  observableAccount.onChange { (_, oldAccount, newAccount) =>
    logger.info(s"*** Model: selected account onchange event: $oldAccount -> $newAccount")
  }

  observablePools.onChange { (_, changes) =>
    logger.info(s"*** Model: observable pools onchange event: $changes")
  }

  observableCleanings.onChange { (_, changes) =>
    logger.info(s"*** Model: observable cleanings onchange event: $changes")
  }

  observableMeasurements.onChange { (_, _) =>
    shouldNotBeInFxThread("via measurements, observable measurements onchange should not be in fx thread.")
    logger.info(s"observable measurements onchange event.")
    Platform.runLater( dashboard() )
  }

  observableChemicals.onChange { (_, changes) =>
    logger.info(s"*** Model: observable chemicals onchange event: $changes")
  }

  def init: Unit =
    logger.info(s"*** Model: initializing ...")
    pools()
    logger.info(s"*** Model: initialized.")

  def onError(message: String): Unit =
    shouldBeInFxThread("onerror message should be in fx thread.")
    observableErrors += Error(message)
    logger.error(message)

  def onError(error: Throwable, message: String): Unit =
    shouldBeInFxThread("onerror error, message should be in fx thread.")
    observableErrors += Error(message)
    logger.error(message, error)

  def pools(): Unit =
    Future {
      shouldNotBeInFxThread("pools should not be in fx thread.")
      // observablePools ++= fetcher.pools()
    }.recover { case error: Throwable => onError(error, s"Loading pools data failed: ${error.getMessage}") }

  def cleanings(poolId: Long): Unit =
    Future {
      shouldNotBeInFxThread("cleanings should not be in fx thread.")
      observableCleanings.clear()
      // observableCleanings ++= fetcher.cleanings(poolId)
    }.recover { case error: Throwable => onError(error, s"Loading cleanings data failed: ${error.getMessage}") }

  def measurements(poolId: Long): Unit =
    Future {
      shouldNotBeInFxThread("measurements should not be in fx thread.")
      observableMeasurements.clear()
      // observableMeasurements ++= fetcher.measurements(poolId) 
    }.recover { case error: Throwable => onError(error, s"Loading measurements data failed: ${error.getMessage}") }

  def chemicals(poolId: Long): Unit =
    Future {
      shouldNotBeInFxThread("chemicals should not be in fx thread.")
      observableChemicals.clear()
      // observableChemicals ++= fetcher.chemicals(poolId) 
    }.recover { case error: Throwable => onError(error, s"Loading chemicals data failed: ${error.getMessage}") }

  def add(pool: Pool): Future[Pool] =
    Future {
      shouldNotBeInFxThread("add pool should not be in fx thread.")
      val newPool = pool // fetcher.add(pool)
      observablePools += newPool
      observablePools.sort()
      selectedPoolId.value = newPool.id
      newPool
    }

  def update(selectedIndex: Int, pool: Pool): Future[Unit] =
    Future {
      shouldNotBeInFxThread("update pool should not be in fx thread.")
      // fetcher.update(pool)
      observablePools.update(selectedIndex, pool)
      observablePools.sort()
      selectedPoolId.value = pool.id
    }

  def add(cleaning: Cleaning): Future[Cleaning] =
    Future {
      shouldNotBeInFxThread("add cleaning should not be in fx thread.")
      val newCleaning = cleaning // fetcher.add(cleaning)
      observableCleanings += newCleaning
      observableCleanings.sort()
      selectedCleaningId.value = newCleaning.id
      newCleaning
    }

  def update(selectedIndex: Int, cleaning: Cleaning): Future[Unit] =
    Future {
      shouldNotBeInFxThread("update cleaning should not be in fx thread.")
      // fetcher.update(cleaning)
      observableCleanings.update(selectedIndex, cleaning)
      observableCleanings.sort()
      selectedCleaningId.value = cleaning.id
    }
  
  def add(measurement: Measurement): Future[Measurement] =
    Future {
      shouldNotBeInFxThread("add measurement should not be in fx thread.")
      val newMeasurement = measurement // fetcher.add(measurement)
      observableMeasurements += newMeasurement
      observableMeasurements.sort()
      selectedMeasurementId.value = newMeasurement.id
      newMeasurement
    }

  def update(selectedIndex: Int, measurement: Measurement): Future[Unit] =
    Future {
      shouldNotBeInFxThread("update measurement should not be in fx thread.")
      // fetcher.update(measurement)
      observableMeasurements.update(selectedIndex, measurement)
      observableMeasurements.sort()
      selectedMeasurementId.value = measurement.id
    }
  
  def add(chemical: Chemical): Future[Chemical] =
    Future {
      shouldNotBeInFxThread("add chemical should not be in fx thread.")
      val newChemical = chemical // fetcher.add(chemical)
      observableChemicals += newChemical
      observableChemicals.sort()
      selectedChemicalId.value = newChemical.id      
      newChemical
    }

  def update(selectedIndex: Int, chemical: Chemical): Future[Unit] =
    Future {
      shouldNotBeInFxThread("update chemical should not be in fx thread.")
      // fetcher.update(chemical)
      observableChemicals.update(selectedIndex, chemical)
      observableChemicals.sort()
      selectedChemicalId.value = chemical.id
    }

  val currentTotalChlorine = ObjectProperty[Int](0)
  val averageTotalChlorine = ObjectProperty[Int](0)
  def totalChlorineInRange(value: Int): Boolean = totalChlorineRange.contains(value)

  val currentFreeChlorine = ObjectProperty[Int](0)
  val averageFreeChlorine = ObjectProperty[Int](0)
  def freeChlorineInRange(value: Int): Boolean = freeChlorineRange.contains(value)

  val currentCombinedChlorine = ObjectProperty[Double](0)
  val averageCombinedChlorine = ObjectProperty[Double](0)
  def combinedChlorineInRange(value: Double): Boolean = combinedChlorineRange.contains(value)

  val currentPh = ObjectProperty[Double](0)
  val averagePh = ObjectProperty[Double](0)
  def phInRange(value: Double): Boolean = phRange.contains(value)

  val currentCalciumHardness = ObjectProperty[Int](0)
  val averageCalciumHardness = ObjectProperty[Int](0)
  def calciumHardnessInRange(value: Int): Boolean = calciumHardnessRange.contains(value)

  val currentTotalAlkalinity = ObjectProperty[Int](0)
  val averageTotalAlkalinity = ObjectProperty[Int](0)
  def totalAlkalinityInRange(value: Int): Boolean = totalAlkalinityRange.contains(value)

  val currentCyanuricAcid = ObjectProperty[Int](0)
  val averageCyanuricAcid = ObjectProperty[Int](0)
  def cyanuricAcidInRange(value: Int): Boolean = cyanuricAcidRange.contains(value)

  val currentTotalBromine = ObjectProperty[Int](0)
  val averageTotalBromine = ObjectProperty[Int](0)
  def totalBromineInRange(value: Int): Boolean = totalBromineRange.contains(value)

  val currentSalt = ObjectProperty[Int](0)
  val averageSalt = ObjectProperty[Int](0)
  def saltInRange(value: Int): Boolean = saltRange.contains(value)

  val currentTemperature = ObjectProperty[Int](0)
  val averageTemperature = ObjectProperty[Int](0)
  def temperatureInRange(value: Int): Boolean = temperatureRange.contains(value)

  private def dashboard(): Unit =
    shouldBeInFxThread("dashboard should be in fx thread.")
    val numberFormat = NumberFormat.getNumberInstance()
    numberFormat.setMaximumFractionDigits(1)
    observableMeasurements.headOption.foreach { measurement =>
      calculateCurrentMeasurements(measurement, numberFormat)
      calculateAverageMeasurements(numberFormat)
    }

  private def calculateCurrentMeasurements(measurement: Measurement, numberFormat: NumberFormat): Unit =
    shouldBeInFxThread("oncurrent should be in fx thread.")
    currentTotalChlorine.value = measurement.totalChlorine
    currentFreeChlorine.value = measurement.freeChlorine
    currentCombinedChlorine.value = numberFormat.format( measurement.combinedChlorine ).toDouble
    currentPh.value = numberFormat.format( measurement.ph ).toDouble
    currentCalciumHardness.value = measurement.calciumHardness
    currentTotalAlkalinity.value = measurement.totalAlkalinity
    currentCyanuricAcid.value = measurement.cyanuricAcid
    currentTotalBromine.value = measurement.totalBromine
    currentSalt.value = measurement.salt
    currentTemperature.value = measurement.temperature

  private def calculateAverageMeasurements(numberFormat: NumberFormat): Unit =
    shouldBeInFxThread("onaverage should be in fx thread.")
    val count = observableMeasurements.length
    averageTotalChlorine.value = observableMeasurements.map(_.totalChlorine).sum / count
    averageFreeChlorine.value = observableMeasurements.map(_.freeChlorine).sum / count
    averageCombinedChlorine.value = numberFormat.format( observableMeasurements.map(_.combinedChlorine).sum / count ).toDouble
    averagePh.value = numberFormat.format( observableMeasurements.map(_.ph).sum / count ).toDouble
    averageCalciumHardness.value = observableMeasurements.map(_.calciumHardness).sum / count
    averageTotalAlkalinity.value = observableMeasurements.map(_.totalAlkalinity).sum / count
    averageCyanuricAcid.value = observableMeasurements.map(_.cyanuricAcid).sum / count
    averageTotalBromine.value = observableMeasurements.map(_.totalBromine).sum / count
    averageSalt.value = observableMeasurements.map(_.salt).sum / count
    averageTemperature.value = observableMeasurements.map(_.temperature).sum / count