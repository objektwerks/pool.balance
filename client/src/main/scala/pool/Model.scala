package pool

import com.typesafe.scalalogging.LazyLogging

import java.text.NumberFormat

import ox.supervised

import scalafx.application.Platform
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.ObjectProperty

import Entity.given
import Fault.given
import Measurement.*

final class Model(fetcher: Fetcher) extends LazyLogging:
  def assertInFxThread(message: String, suffix: String = " should be in fx thread!"): Unit =
    require(Platform.isFxApplicationThread, message + suffix)
  def assertNotInFxThread(message: String, suffix: String = " should not be in fx thread!"): Unit =
    require(!Platform.isFxApplicationThread, message + suffix)

  val registered = ObjectProperty[Boolean](true)
  val loggedin = ObjectProperty[Boolean](true)

  val selectedPoolId = ObjectProperty[Long](0)
  val selectedCleaningId = ObjectProperty[Long](0)
  val selectedMeasurementId = ObjectProperty[Long](0)
  val selectedChemicalId = ObjectProperty[Long](0)

  selectedPoolId.onChange { (_, _, newPoolId) =>
    cleanings(newPoolId)
    measurements(newPoolId)
    chemicals(newPoolId)
  }

  val objectAccount = ObjectProperty[Account](Account.empty)
  val observablePools = ObservableBuffer[Pool]()
  val observableCleanings = ObservableBuffer[Cleaning]()
  val observableMeasurements = ObservableBuffer[Measurement]()
  val observableChemicals = ObservableBuffer[Chemical]()
  val observableFaults = ObservableBuffer[Fault]()

  observableMeasurements.onChange { (_, _) =>
    Platform.runLater( dashboard() )
  }

  def onFetchFault(source: String, fault: Fault): Unit =
    val cause = s"$source - $fault"
    logger.error(s"*** cause: $cause")
    observableFaults += fault.copy(cause = cause)

  def onFetchFault(source: String, entity: Entity, fault: Fault): Unit =
    val cause = s"$source - $entity - $fault"
    logger.error(s"*** cause: $cause")
    observableFaults += fault.copy(cause = cause)

  def add(fault: Fault): Unit =
    supervised:
      assertNotInFxThread(s"add fault: $fault")
      fetcher.fetch(
        AddFault(objectAccount.get.license, fault),
        (event: Event) => event match
          case fault @ Fault(cause, _) => onFetchFault("add fault", fault)
          case FaultAdded() =>
            observableFaults += fault
            observableFaults.sort()
          case _ => ()
      )

  def register(register: Register): Unit =
    supervised:
      assertNotInFxThread(s"register: $register")
      fetcher.fetch(
        register,
        (event: Event) => event match
          case _ @ Fault(_, _) => registered.set(false)
          case Registered(account) => objectAccount.set(account)
          case _ => ()
      )

  def login(login: Login): Unit =
    supervised:
      assertNotInFxThread(s"login: $login")
      fetcher.fetch(
        login,
        (event: Event) => event match
          case _ @ Fault(_, _) => loggedin.set(false)
          case LoggedIn(account) =>
            objectAccount.set(account)
            pools()
          case _ => ()
      )

  def deactivate(deactivate: Deactivate): Unit =
    supervised:
      assertNotInFxThread(s"deactivate: $deactivate")
      fetcher.fetch(
        deactivate,
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("deactivate", fault)
          case Deactivated(account) => objectAccount.set(account)
          case _ => ()
      )

  def reactivate(reactivate: Reactivate): Unit =
    supervised:
      assertNotInFxThread(s"reactivate: $reactivate")
      fetcher.fetch(
        reactivate,
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("reactivate", fault)
          case Reactivated(account) => objectAccount.set(account)
          case _ => ()
      )

  def pools(): Unit =
    supervised:
      assertNotInFxThread("list pools")
      fetcher.fetch(
        ListPools(objectAccount.get.license),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("pools", fault)
          case PoolsListed(pools) =>
            observablePools.clear()
            observablePools ++= pools
          case _ => ()
      )

  def add(pool: Pool)(runLast: => Unit): Unit =
    supervised:
      assertNotInFxThread(s"add pool: $pool")
      fetcher.fetch(
        SavePool(objectAccount.get.license, pool),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("add pool", pool, fault)
          case PoolSaved(id) =>
            observablePools.insert(0, pool.copy(id = id))
            observablePools.sort()
            selectedPoolId.set(id)
            logger.info(s"Added pool: $pool")
            runLast
          case _ => ()
      )

  def update(selectedIndex: Int, pool: Pool)(runLast: => Unit): Unit =
    supervised:
      assertNotInFxThread(s"update pool from: $selectedIndex to: $pool")
      fetcher.fetch(
        SavePool(objectAccount.get.license, pool),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("update pool", pool, fault)
          case PoolSaved(id) =>
            observablePools.update(selectedIndex, pool)
            logger.info(s"Updated pool from: $selectedIndex to: $pool")
            runLast
          case _ => ()
      )

  def cleanings(poolId: Long): Unit =
    supervised:
      assertNotInFxThread(s"list cleanings, pool id: $poolId")
      fetcher.fetch(
        ListCleanings(objectAccount.get.license, poolId),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("cleanings", fault)
          case CleaningsListed(cleanings) =>
            observableCleanings.clear()
            observableCleanings ++= cleanings
          case _ => ()
      )

  def add(cleaning: Cleaning)(runLast: => Unit): Unit =
    supervised:
      assertNotInFxThread(s"add cleaning: $cleaning")
      fetcher.fetch(
        SaveCleaning(objectAccount.get.license, cleaning),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("add cleaning", cleaning, fault)
          case CleaningSaved(id) =>
            observableCleanings.insert(0, cleaning.copy(id = id))
            observableCleanings.sort()
            selectedCleaningId.set(id)
            logger.info(s"Added cleaning: $cleaning")
            runLast
          case _ => ()
      )

  def update(selectedIndex: Int, cleaning: Cleaning)(runLast: => Unit): Unit =
    supervised:
      assertNotInFxThread(s"update session from: $selectedIndex to: $cleaning")
      fetcher.fetch(
        SaveCleaning(objectAccount.get.license, cleaning),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("update cleaning", cleaning, fault)
          case CleaningSaved(id) =>
            if selectedIndex > -1 then
              observableCleanings.update(selectedIndex, cleaning)
              logger.info(s"Updated cleaning from: $selectedIndex to: $cleaning")
              runLast
            else
              logger.error(s"Update of cleaning: $cleaning \nfailed due to invalid index: $selectedIndex")
          case _ => ()
      )

  def measurements(poolId: Long): Unit =
    supervised:
      assertNotInFxThread(s"list measurements, pool id: $poolId")
      fetcher.fetch(
        ListMeasurements(objectAccount.get.license, poolId),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("measurements", fault)
          case MeasurementsListed(measurements) =>
            observableMeasurements.clear()
            observableMeasurements ++= measurements
          case _ => ()
      )

  def add(measurement: Measurement)(runLast: => Unit): Unit =
    supervised:
      assertNotInFxThread(s"add measurement: $measurement")
      fetcher.fetch(
        SaveMeasurement(objectAccount.get.license, measurement),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("add measurement", measurement, fault)
          case MeasurementSaved(id) =>
            observableMeasurements.insert(0, measurement.copy(id = id))
            observableMeasurements.sort()
            selectedMeasurementId.set(id)
            logger.info(s"Added measurement: $measurement")
            runLast
          case _ => ()
      )

  def update(selectedIndex: Int, measurement: Measurement)(runLast: => Unit): Unit =
    supervised:
      assertNotInFxThread(s"update measurement from: $selectedIndex to: $measurement")
      fetcher.fetch(
        SaveMeasurement(objectAccount.get.license, measurement),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("Model.save measurement", measurement, fault)
          case MeasurementSaved(id) =>
            if selectedIndex > -1 then
              observableMeasurements.update(selectedIndex, measurement)
              logger.info(s"Updated measurement from: $selectedIndex to: $measurement")
              runLast
            else
              logger.error(s"Update of measurement: $measurement \nfailed due to invalid index: $selectedIndex")
          case _ => ()
      )

  def chemicals(poolId: Long): Unit =
    supervised:
      assertNotInFxThread(s"list chemicals, pool id: $poolId")
      fetcher.fetch(
        ListChemicals(objectAccount.get.license, poolId),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("chemicals", fault)
          case ChemicalsListed(chemicals) =>
            observableChemicals.clear()
            observableChemicals ++= chemicals
          case _ => ()
      )
  
  def add(chemical: Chemical)(runLast: => Unit): Unit =
    supervised:
      assertNotInFxThread(s"add chemical: $chemical")
      fetcher.fetch(
        SaveChemical(objectAccount.get.license, chemical),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("add chemical", chemical, fault)
          case ChemicalSaved(id) =>
            observableChemicals.insert(0, chemical.copy(id = id))
            observableChemicals.sort()
            selectedChemicalId.set(id)
            logger.info(s"Added chemical: $chemical")
            runLast
          case _ => ()
      )

  def update(selectedIndex: Int, chemical: Chemical)(runLast: => Unit): Unit =
    supervised:
      assertNotInFxThread(s"update chemical from: $selectedIndex to: $chemical")
      fetcher.fetch(
        SaveChemical(objectAccount.get.license, chemical),
        (event: Event) => event match
          case fault @ Fault(_, _) => onFetchFault("update chemical", chemical, fault)
          case ChemicalSaved(id) =>
            if selectedIndex > -1 then
              observableChemicals.update(selectedIndex, chemical)
              logger.info(s"Updated chemical from: $selectedIndex to: $chemical")
              runLast
            else
              logger.error(s"Update of chemical: $chemical \nfailed due to invalid index: $selectedIndex")
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
    assertInFxThread("dashboard.")
    val numberFormat = NumberFormat.getNumberInstance()
    numberFormat.setMaximumFractionDigits(1)
    observableMeasurements.headOption.foreach { measurement =>
      calculateCurrentMeasurements(measurement, numberFormat)
      calculateAverageMeasurements(numberFormat)
    }

  private def calculateCurrentMeasurements(measurement: Measurement, numberFormat: NumberFormat): Unit =
    assertInFxThread("calculateCurrentMeasurements.")
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
    assertInFxThread("calculateAverageMeasurements.")
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