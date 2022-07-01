package pool

import com.typesafe.scalalogging.LazyLogging

import java.text.NumberFormat

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
    println(s"selected pool id onchange event in fx thread: ${Platform.isFxApplicationThread}")
    logger.info(s"selected oool id onchange event: $oldPoolId -> $newPoolId")
    cleanings(newPoolId)
    measurements(newPoolId)
    chemicals(newPoolId)
  }

  observableMeasurements.onChange{ (_, _) =>
    println(s"observable measurements onchange event in fx thread: ${Platform.isFxApplicationThread}")
    logger.info(s"observable measurements onchange event.")
    dashboard()
  }

  pools()

  private def pools(): Unit =
    Try {
      println(s"pools in fx thread: ${Platform.isFxApplicationThread}")
      observablePools ++= store.pools()
    }.recover { case error: Throwable => onError(error, s"Loading pools data failed: ${error.getMessage}") }

  private def cleanings(poolId: Long): Unit =
    Try {
      println(s"cleanings in fx thread: ${Platform.isFxApplicationThread}")
      observableCleanings.clear()
      observableCleanings ++= store.cleanings(poolId)
    }.recover { case error: Throwable => onError(error, s"Loading cleanings data failed: ${error.getMessage}") }

  private def measurements(poolId: Long): Unit =
    Try {
      println(s"measurements in fx thread: ${Platform.isFxApplicationThread}")
      observableMeasurements.clear()
      observableMeasurements ++= store.measurements(poolId) 
    }.recover { case error: Throwable => onError(error, s"Loading measurements data failed: ${error.getMessage}") }

  private def chemicals(poolId: Long): Unit =
    Try {
      println(s"chemicals in fx thread: ${Platform.isFxApplicationThread}")
      observableChemicals.clear()
      observableChemicals ++= store.chemicals(poolId) 
    }.recover { case error: Throwable => onError(error, s"Loading chemicals data failed: ${error.getMessage}") }

  private def dashboard(): Unit =
    Try {
      println(s"dashboard in fx thread: ${Platform.isFxApplicationThread}")
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
    }.recover { case error: Throwable => onError(error, s"Loading dashboard data failed: ${error.getMessage}") }

  def onError(message: String): Unit =
    println(s"on error message in fx thread: ${Platform.isFxApplicationThread}")
    observableErrors += message
    logger.error(message)

  def onError(error: Throwable, message: String): Unit =
    println(s"on error throwable, message in fx thread: ${Platform.isFxApplicationThread}")
    observableErrors += message
    logger.error(message, error)

  def add(pool: Pool): Either[Throwable, Pool] =
    Try {
      println(s"add pool in fx thread: ${Platform.isFxApplicationThread}")
      val newPool = store.add(pool)
      observablePools += newPool
      observablePools.sort()
      selectedPoolId.value = newPool.id
      newPool
    }.toEither

  def update(selectedIndex: Int, pool: Pool): Either[Throwable, Unit] =
    Try {
      println(s"update pool in fx thread: ${Platform.isFxApplicationThread}")
      store.update(pool)
      observablePools.update(selectedIndex, pool)
      observablePools.sort()
      selectedPoolId.value = pool.id
    }.toEither

  def add(cleaning: Cleaning): Either[Throwable, Cleaning] =
    Try {
      println(s"add cleaning in fx thread: ${Platform.isFxApplicationThread}")
      val newCleaning = store.add(cleaning)
      observableCleanings += newCleaning
      observableCleanings.sort()
      selectedCleaningId.value = newCleaning.id
      newCleaning
    }.toEither

  def update(selectedIndex: Int, cleaning: Cleaning): Either[Throwable, Unit] =
    Try {
      println(s"update cleaning in fx thread: ${Platform.isFxApplicationThread}")
      store.update(cleaning)
      observableCleanings.update(selectedIndex, cleaning)
      observableCleanings.sort()
      selectedCleaningId.value = cleaning.id
    }.toEither
  
  def add(measurement: Measurement): Either[Throwable, Measurement] =
    Try {
      println(s"add measurement in fx thread: ${Platform.isFxApplicationThread}")
      val newMeasurement = store.add(measurement)
      observableMeasurements += newMeasurement
      observableMeasurements.sort()
      selectedMeasurementId.value = newMeasurement.id
      newMeasurement
    }.toEither

  def update(selectedIndex: Int, measurement: Measurement): Either[Throwable, Unit] =
    Try {
      println(s"update measurement in fx thread: ${Platform.isFxApplicationThread}")
      store.update(measurement)
      observableMeasurements.update(selectedIndex, measurement)
      observableMeasurements.sort()
      selectedMeasurementId.value = measurement.id
    }.toEither
  
  def add(chemical: Chemical): Either[Throwable, Chemical] =
    Try {
      println(s"add chemical in fx thread: ${Platform.isFxApplicationThread}")
      val newChemical = store.add(chemical)
      observableChemicals += newChemical
      observableChemicals.sort()
      selectedChemicalId.value = newChemical.id      
      newChemical
    }.toEither

  def update(selectedIndex: Int, chemical: Chemical): Either[Throwable, Unit] =
    Try {
      println(s"update chemical in fx thread: ${Platform.isFxApplicationThread}")
      store.update(chemical)
      observableChemicals.update(selectedIndex, chemical)
      observableChemicals.sort()
      selectedChemicalId.value = chemical.id
    }.toEither