package pool

import com.typesafe.config.ConfigFactory

import java.time.LocalDate

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

final class StoreTest extends AnyFunSuite with Matchers:
  val store = Context( ConfigFactory.load("test.conf") ).store

  test("store") {
    var pool = addPool()
    pool = updatePool(pool)
    listPools(pool)

    var cleaning = addCleaning(pool)
    cleaning = updateCleaning(cleaning)
    listCleanings(pool, cleaning)
    
    var measurement = addMeasurement(pool)
    measurement = updateMeasurement(measurement)
    listMeasurements(pool, measurement)

    var chemical = addChemical(pool)
    chemical = updateChemical(chemical)
    listChemicals(pool, chemical)
  }

  def addPool(): Pool =
    val pool = Pool(name = "pool-a", volume = 10000, unit = UnitOfMeasure.gl)
    val addedPool = store.add(pool)
    addedPool.id should not be 0
    addedPool

  def updatePool(pool: Pool): Pool =
    val updatedPool = pool.copy(volume = 11000)
    store.update(updatedPool)
    updatedPool

  def listPools(pool: Pool): Unit = 
    val pools = store.pools()
    pools.length shouldBe 1
    pools.head shouldBe pool

  def addCleaning(pool: Pool): Cleaning =
    val cleaning = Cleaning(poolId = pool.id, vacuum = true)
    val addedCleaning = store.add(cleaning)
    addedCleaning.id should not be 0
    addedCleaning

  def updateCleaning(cleaning: Cleaning): Cleaning =
    val updatedCleaning = cleaning.copy(net = true)
    store.update(updatedCleaning)
    updatedCleaning

  def listCleanings(pool: Pool, cleaning: Cleaning): Unit =
    val cleanings = store.cleanings(pool.id)
    cleanings.length shouldBe 1
    cleanings.head shouldBe cleaning

  def addMeasurement(pool: Pool): Measurement =
    val measurement = Measurement(poolId = pool.id)
    val addedMeasurement = store.add(measurement)
    addedMeasurement.id should not be 0
    addedMeasurement

  def updateMeasurement(measurement: Measurement): Measurement =
    val updatedMeasurement = measurement.copy(ph = 7.3)
    store.update(updatedMeasurement)
    updatedMeasurement

  def listMeasurements(pool: Pool, measurement: Measurement): Unit =
    val measurements = store.measurements(pool.id)
    measurements.length shouldBe 1
    measurements.head shouldBe measurement

  def addChemical(pool: Pool): Chemical =
    val chemical = Chemical(poolId = pool.id, typeof = TypeOfChemical.Trichlor, amount = 2.0, unit = UnitOfMeasure.gl)
    val addedChemcial = store.add(chemical)
    addedChemcial.id should not be 0
    addedChemcial

  def updateChemical(chemical: Chemical): Chemical =
    val updatedChemical = chemical.copy(amount = 2.5)
    store.update(updatedChemical)
    updatedChemical

  def listChemicals(pool: Pool, chemical: Chemical): Unit =
    val chemicals = store.chemicals(pool.id)
    chemicals.length shouldBe 1
    chemicals.head shouldBe chemical