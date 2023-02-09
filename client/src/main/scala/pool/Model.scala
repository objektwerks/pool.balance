package pool

import com.typesafe.scalalogging.LazyLogging

import java.text.NumberFormat
import java.util.concurrent.Executors

import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.ObjectProperty
import scala.util.Try

import Entity.given
import Measurement.*

final class Model(fetcher: Fetcher) extends LazyLogging:
  val shouldBeInFxThread = (message: String) => require(Platform.isFxApplicationThread, message)
  val shouldNotBeInFxThread = (message: String) => require(!Platform.isFxApplicationThread, message)

  val registered = ObjectProperty[Boolean](true)
  val loggedin = ObjectProperty[Boolean](true)

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

  val observableAccount = ObjectProperty[Account](Account.empty)
  val observablePools = ObservableBuffer[Pool]()
  val observableCleanings = ObservableBuffer[Cleaning]()
  val observableMeasurements = ObservableBuffer[Measurement]()
  val observableChemicals = ObservableBuffer[Chemical]()
  val observableFaults = ObservableBuffer[Fault]()

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
    shouldNotBeInFxThread("observable measurements onchange should not be in fx thread.")
    logger.info(s"observable measurements onchange event.")
    Platform.runLater( dashboard() )
  }

  observableChemicals.onChange { (_, changes) =>
    logger.info(s"*** Model: observable chemicals onchange event: $changes")
  }

  def onFault(cause: String): Unit =
    observableFaults += Fault(cause)
    logger.error(cause)

  def onFault(error: Throwable, cause: String): Unit =
    observableFaults += Fault(cause)
    logger.error(cause, error)

  def onFault(source: String, fault: Fault): Unit =
    observableFaults += fault
    logger.error(s"*** $source - $fault")

  def onFault(source: String, entity: Entity, fault: Fault): Unit =
    observableFaults += fault
    logger.error(s"*** $source - $entity - $fault")

  def register(register: Register): Unit =
    fetcher.fetch(
      register,
      (event: Event) => event match
        case fault @ Fault(_, _) => registered.set(false)
        case Registered(account) => observableAccount.set(account)
        case _ => ()
    )

  def login(login: Login): Unit =
    fetcher.fetch(
      login,
      (event: Event) => event match
        case fault @ Fault(_, _) => loggedin.set(false)
        case LoggedIn(account) => observableAccount.set(account)
        case _ => ()
    )

  def deactivate(deactivate: Deactivate): Unit =
    fetcher.fetchAsync(
      deactivate,
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.deactivate", fault)
        case Deactivated(account) => observableAccount.set(account)
        case _ => ()
    )

  def reactivate(reactivate: Reactivate): Unit =
    fetcher.fetchAsync(
      reactivate,
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.reactivate", fault)
        case Reactivated(account) => observableAccount.set(account)
        case _ => ()
    )

  def pools(): Unit = {}
    fetcher.fetchAsync(
      ListPools(observableAccount.get.license),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.pools", fault)
        case PoolsListed(pools) =>
          observablePools.clear()
          observablePools ++= pools
        case _ => ()
    )

  def save(pool: Pool): Unit =
    fetcher.fetchAsync(
      SavePool(observableAccount.get.license, pool),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.save pool", pool, fault)
        case PoolSaved(id) =>
          if pool.id == 0 then observablePools += pool.copy(id = id)
          else observablePools.update(observablePools.indexOf(pool), pool)
          observablePools.sort()
          selectedPoolId.set(pool.id)
        case _ => ()
    )

  def cleanings(poolId: Long): Unit =
    fetcher.fetchAsync(
      ListCleanings(observableAccount.get.license, poolId),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.cleanings", fault)
        case CleaningsListed(cleanings) =>
          observableCleanings.clear()
          observableCleanings ++= cleanings
        case _ => ()
    )

  def save(cleaning: Cleaning): Unit =
    fetcher.fetchAsync(
      SaveCleaning(observableAccount.get.license, cleaning),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.save cleaning", cleaning, fault)
        case CleaningSaved(id) =>
          if cleaning.id == 0 then observableCleanings += cleaning.copy(id = id)
          else observableCleanings.update(observableCleanings.indexOf(cleaning), cleaning)
          observableCleanings.sort()
          selectedCleaningId.set(cleaning.id)
        case _ => ()
    )

  def measurements(poolId: Long): Unit =
    fetcher.fetchAsync(
      ListMeasurements(observableAccount.get.license, poolId),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.measurements", fault)
        case MeasurementsListed(measurements) =>
          observableMeasurements.clear()
          observableMeasurements ++= measurements
        case _ => ()
    )

  def save(measurement: Measurement): Unit =
    fetcher.fetchAsync(
      SaveMeasurement(observableAccount.get.license, measurement),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.save measurement", measurement, fault)
        case MeasurementSaved(id) =>
          if measurement.id == 0 then observableMeasurements += measurement.copy(id = id)
          else observableMeasurements.update(observableMeasurements.indexOf(measurement), measurement)
          observableMeasurements.sort()
          selectedMeasurementId.set(measurement.id)
        case _ => ()
    )

  def chemicals(poolId: Long): Unit =
    fetcher.fetchAsync(
      ListChemicals(observableAccount.get.license, poolId),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.chemicals", fault)
        case ChemicalsListed(chemicals) =>
          observableChemicals.clear()
          observableChemicals ++= chemicals
        case _ => ()
    )
  
  def save(chemical: Chemical): Unit =
    fetcher.fetchAsync(
      SaveChemical(observableAccount.get.license, chemical),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.save chemical", chemical, fault)
        case ChemicalSaved(id) =>
          if chemical.id == 0 then observableChemicals += chemical.copy(id = id)
          else observableChemicals.update(observableChemicals.indexOf(chemical), chemical)
          observableChemicals.sort()
          selectedChemicalId.set(chemical.id)
        case _ => ()
    )

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