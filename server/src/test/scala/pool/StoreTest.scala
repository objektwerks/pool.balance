package pool

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.*
import scala.sys.process.Process

final class StoreTest extends AnyFunSuite with Matchers:
  val exitCode = Process("psql -d poolbalance -f ddl.sql").run().exitValue()

  val store = Store( ConfigFactory.load("test.conf"), Store.cache(minSize = 1, maxSize = 2, expireAfter = 1.hour) )

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
    val pool = Pool(name = "pool-a", volume = 10000, unit = UnitOfMeasure.gl.toString)
    val id = store.addPool(pool)
    id should not be 0
    pool.copy(id = id)

  def updatePool(pool: Pool): Pool =
    val updatedPool = pool.copy(volume = 11000)
    store.updatePool(updatedPool)
    updatedPool

  def listPools(pool: Pool): Unit = 
    val pools = store.listPools()
    pools.length shouldBe 1
    pools.head shouldBe pool

  def addCleaning(pool: Pool): Cleaning =
    val cleaning = Cleaning(poolId = pool.id, vacuum = true)
    val id = store.addCleaning(cleaning)
    id should not be 0
    cleaning.copy(id = id)

  def updateCleaning(cleaning: Cleaning): Cleaning =
    val updatedCleaning = cleaning.copy(net = true)
    store.updateCleaning(updatedCleaning)
    updatedCleaning

  def listCleanings(pool: Pool, cleaning: Cleaning): Unit =
    val cleanings = store.listCleanings(pool.id)
    cleanings.length shouldBe 1
    cleanings.head shouldBe cleaning

  def addMeasurement(pool: Pool): Measurement =
    val measurement = Measurement(poolId = pool.id)
    val id = store.addMeasurement(measurement)
    id should not be 0
    measurement.copy(id = id)

  def updateMeasurement(measurement: Measurement): Measurement =
    val updatedMeasurement = measurement.copy(ph = 7.3)
    store.updateMeasurement(updatedMeasurement)
    updatedMeasurement

  def listMeasurements(pool: Pool, measurement: Measurement): Unit =
    val measurements = store.listMeasurements(pool.id)
    measurements.length shouldBe 1
    measurements.head shouldBe measurement

  def addChemical(pool: Pool): Chemical =
    val chemical = Chemical(poolId = pool.id, typeof = TypeOfChemical.Trichlor.toString, amount = 2.0, unit = UnitOfMeasure.gl.toString)
    val id = store.addChemical(chemical)
    id should not be 0
    chemical.copy(id = id)

  def updateChemical(chemical: Chemical): Chemical =
    val updatedChemical = chemical.copy(amount = 2.5)
    store.updateChemical(updatedChemical)
    updatedChemical

  def listChemicals(pool: Pool, chemical: Chemical): Unit =
    val chemicals = store.listChemicals(pool.id)
    chemicals.length shouldBe 1
    chemicals.head shouldBe chemical