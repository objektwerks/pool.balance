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

    var measurement = addMeasurement(pool)
    measurement = updateMeasurement(measurement)
    listMeasurements(measurement)
  }

  def addPool(): Pool =
    val pool = Pool(name = "pool-a", built = LocalDate.now(), volume = 10000, unit = unitOfMeasure.gl)
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

  def addMeasurement(pool: Pool): Measurement =
    val measurement = Measurement(poolId = pool.id, typeof = typeOfMeasurement.pH, measurement = 7.3)
    val addedMeasurement = store.add(measurement)
    addedMeasurement.id should not be 0
    addedMeasurement

  def updateMeasurement(measurement: Measurement): Measurement =
    val updatedMeasurement = measurement.copy(measurement = 7.3)
    store.update(updatedMeasurement)
    updatedMeasurement

  def listMeasurements(measurement: Measurement): Unit =
    val measurements = store.measurements(measurement.typeof)
    measurements.length shouldBe 1
    measurements.head shouldBe measurement