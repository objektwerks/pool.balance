package pool

import com.typesafe.scalalogging.LazyLogging

import java.text.NumberFormat
import java.util.concurrent.Executors

import scalafx.Includes.*
import scala.util.Try

import scalafx.application.Platform
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.ObjectProperty

import Entity.given
import Measurement.*

final class Model(fetcher: Fetcher) extends LazyLogging:
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
    shouldNotBeInFxThread("register should not be in fx thread.")
    fetcher.call(
      register,
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.register", fault)
        case Registered(account) => observableAccount.set(account)
        case _ => ()
    )

  def login(login: Login): Unit =
    shouldNotBeInFxThread("login should not be in fx thread.")
    fetcher.call(
      login,
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.login", fault)
        case LoggedIn(account) => observableAccount.set(account)
        case _ => ()
    )

  def deactivate(deactivate: Deactivate): Unit =
    shouldNotBeInFxThread("deactivate should not be in fx thread.")
    fetcher.call(
      deactivate,
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.deactivate", fault)
        case Deactivated(account) => observableAccount.set(account)
        case _ => ()
    )

  def reactivate(reactivate: Reactivate): Unit =
    shouldNotBeInFxThread("reactivate should not be in fx thread.")
    fetcher.call(
      reactivate,
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.reactivate", fault)
        case Reactivated(account) => observableAccount.set(account)
        case _ => ()
    )

  def pools(): Unit =
    shouldNotBeInFxThread("pools should not be in fx thread.")
    fetcher.call(
      ListPools(observableAccount.get.license),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.pools", fault)
        case PoolsListed(pools) =>
          observablePools.clear()
          observablePools ++= pools
        case _ => ()
    )

  def add(pool: Pool): Unit =
    shouldNotBeInFxThread("pool add should not be in fx thread.")
    fetcher.call(
      SavePool(observableAccount.get.license, pool),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.add pool", pool, fault)
        case PoolSaved(id) => observablePools += pool.copy(id = id)
        case _ => ()
    )

  def update(pool: Pool): Unit =
    shouldNotBeInFxThread("pool update should not be in fx thread.")
    fetcher.call(
      SavePool(observableAccount.get.license, pool),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.update pool", pool, fault)
        case PoolSaved(id) => observablePools.update(observablePools.indexOf(pool), pool)
        case _ => ()
    )

  def cleanings(poolId: Long): Unit =
    shouldNotBeInFxThread("cleanings should not be in fx thread.")
    fetcher.call(
      ListCleanings(observableAccount.get.license, poolId),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.cleanings", fault)
        case CleaningsListed(cleanings) =>
          observableCleanings.clear()
          observableCleanings ++= cleanings
        case _ => ()
    )

  def add(cleaning: Cleaning): Unit =
    shouldNotBeInFxThread("cleaing add should not be in fx thread.")
    fetcher.call(
      SaveCleaning(observableAccount.get.license, cleaning),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.add cleaning", cleaning, fault)
        case CleaningSaved(id) => observableCleanings += cleaning.copy(id = id)
        case _ => ()
    )

  def update(cleaning: Cleaning): Unit =
    shouldNotBeInFxThread("cleaning update should not be in fx thread.")
    fetcher.call(
      SaveCleaning(observableAccount.get.license, cleaning),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.update cleaning", cleaning, fault)
        case CleaningSaved(id) => observableCleanings.update(observableCleanings.indexOf(cleaning), cleaning)
        case _ => ()
    )

  def measurements(poolId: Long): Unit =
    shouldNotBeInFxThread("measurements should not be in fx thread.")
    fetcher.call(
      ListMeasurements(observableAccount.get.license, poolId),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.measurements", fault)
        case MeasurementsListed(measurements) =>
          observableMeasurements.clear()
          observableMeasurements ++= measurements
        case _ => ()
    )

  def add(measurement: Measurement): Unit =
    shouldNotBeInFxThread("measurement add should not be in fx thread.")
    fetcher.call(
      SaveMeasurement(observableAccount.get.license, measurement),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.add measurement", measurement, fault)
        case MeasurementSaved(id) => observableMeasurements += measurement.copy(id = id)
        case _ => ()
    )

  def update(measurement: Measurement): Unit =
    shouldNotBeInFxThread("measurement update should not be in fx thread.")
    fetcher.call(
      SaveMeasurement(observableAccount.get.license, measurement),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.update measurement", measurement, fault)
        case MeasurementSaved(id) => observableMeasurements.update(observableMeasurements.indexOf(measurement), measurement)
        case _ => ()
    )

  def chemicals(poolId: Long): Unit =
    shouldNotBeInFxThread("chemicals should not be in fx thread.")
    fetcher.call(
      ListChemicals(observableAccount.get.license, poolId),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.chemicals", fault)
        case ChemicalsListed(chemicals) =>
          observableChemicals.clear()
          observableChemicals ++= chemicals
        case _ => ()
    )
  
  def add(chemical: Chemical): Unit =
    shouldNotBeInFxThread("chemical add should not be in fx thread.")
    fetcher.call(
      SaveChemical(observableAccount.get.license, chemical),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.add chemical", chemical, fault)
        case ChemicalSaved(id) => observableChemicals += chemical.copy(id = id)
        case _ => ()
    )

  def update(chemical: Chemical): Unit =
    shouldNotBeInFxThread("chemical update should not be in fx thread.")
    fetcher.call(
      SaveChemical(observableAccount.get.license, chemical),
      (event: Event) => event match
        case fault @ Fault(_, _) => onFault("Model.update chemical", chemical, fault)
        case ChemicalSaved(id) => observableChemicals.update(observableChemicals.indexOf(chemical), chemical)
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