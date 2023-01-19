package pool

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.*
import scala.sys.process.Process

import Validator.*

class IntegrationTest extends AnyFunSuite with Matchers:
  val exitCode = Process("psql -d poolbalance -f ddl.sql").run().exitValue()
  exitCode shouldBe 0

  val config = ConfigFactory.load("test.conf")

  val store = Store(config, Store.cache(minSize = 1, maxSize = 1, expireAfter = 1.hour))
  val emailer = Emailer(config)
  val dispatcher = Dispatcher(store, emailer)

  var testAccount = Account()
  var testPool = Pool()
  var testCleaning = Cleaning(poolId = 0)
  var testMeasurement = Measurement(poolId = 0)
  var testChemical = Chemical(poolId = 0)

  test("integration") {
    register
    login

    deactivate
    reactivate

    addPool
    updatePool
    listPools

    addCleaning
    updateCleaning
    listCleanings

    addMeasurement
    updateMeasurement
    listMeasurements

    addChemical
    updateChemical
    listChemicals
  }

  def register: Unit =
    val register = Register(config.getString("email.sender"))
    dispatcher.dispatch(register) match
      case Registered(account) =>
        assert( account.isActivated )
        testAccount = account
      case _ => fail("Invalid registered event.")
    
  def login: Unit =
    val login = Login(testAccount.emailAddress, testAccount.pin)
    dispatcher.dispatch(login) match
      case LoggedIn(account) => account shouldBe testAccount
      case _ => fail("Invalid loggedin event.")

  def deactivate: Unit =
    val deactivate = Deactivate(testAccount.license)
    dispatcher.dispatch(deactivate) match
      case Deactivated(account) => assert( account.isDeactivated )
      case _ => fail("Invalid deactivated event.")

  def reactivate: Unit =
    val reactivate = Reactivate(testAccount.license)
    dispatcher.dispatch(reactivate) match
      case Reactivated(account) => assert( account.isActivated )
      case _ => fail("Invalid reactivated event.")

  def addPool: Unit =
    testPool = Pool(id = 0, name = "a", volume = 8000, unit = UnitOfMeasure.gl.toString)
    val savePool = SavePool(testAccount.license, testPool)
    dispatcher.dispatch(savePool) match
      case PoolSaved(id) =>
        id should not be 0
        testPool = testPool.copy(id = id)
        testCleaning = testCleaning.copy(poolId = id)
        testMeasurement = testMeasurement.copy(poolId = id)
        testChemical = testChemical.copy(poolId = id)
      case _ => fail("Invalid pool saved event.")

  def updatePool: Unit =
    testPool = testPool.copy(volume = 10000)
    val savePool = SavePool(testAccount.license, testPool)
    dispatcher.dispatch(savePool) match
      case PoolSaved(id) => id shouldBe testPool.id
      case _ => fail("Invalid pool saved event.")
    
  def listPools: Unit =
    val listPools = ListPools(testAccount.license)
    dispatcher.dispatch(listPools) match
      case PoolsListed(pools) =>
        pools.length shouldBe 1
        pools.head shouldBe testPool
      case _ => fail("Invalid pools listed event.")

  def addCleaning: Unit =
    val saveCleaning = SaveCleaning(testAccount.license, testCleaning)
    dispatcher.dispatch(saveCleaning) match
      case CleaningSaved(id) =>
        id should not be 0
        testCleaning = testCleaning.copy(id = id)
      case _ => fail("Invalid cleaning saved event.")

  def updateCleaning: Unit =
    testCleaning = testCleaning.copy(vacuum = true)
    val saveCleaning = SaveCleaning(testAccount.license, testCleaning)
    dispatcher.dispatch(saveCleaning) match
      case CleaningSaved(id) =>
        id shouldBe testCleaning.id
      case _ => fail("Invalid cleaning saved event.")

  def listCleanings: Unit =
    val listCleanings = ListCleanings(testAccount.license, testPool.id)
    dispatcher.dispatch(listCleanings) match
      case CleaningsListed(cleanings) =>
        cleanings.length shouldBe 1
        cleanings.head shouldBe testCleaning
      case _ => fail("Invalid cleanings listed event.")
    
  def addMeasurement: Unit =
    val saveMeasurement = SaveMeasurement(testAccount.license, testMeasurement)
    dispatcher.dispatch(saveMeasurement) match
      case MeasurementSaved(id) =>
        id should not be 0
        testMeasurement = testMeasurement.copy(id = id)
      case _ => fail("Invalid measurement saved event.")

  def updateMeasurement: Unit =
    testMeasurement = testMeasurement.copy(temperature = 82)
    val saveMeasurement = SaveMeasurement(testAccount.license, testMeasurement)
    dispatcher.dispatch(saveMeasurement) match
      case MeasurementSaved(id) => id shouldBe testMeasurement.id
      case _ => fail("Invalid measurement saved event.")

  def listMeasurements: Unit =
    val listMeasurements = ListMeasurements(testAccount.license, testPool.id)
    dispatcher.dispatch(listMeasurements) match
      case MeasurementsListed(measurements) =>
        measurements.length shouldBe 1
        measurements.head shouldBe testMeasurement
      case _ => fail("Invalid measurements listed event.")

  def addChemical: Unit =
    val saveChemical = SaveChemical(testAccount.license, testChemical)
    dispatcher.dispatch(saveChemical) match
      case ChemicalSaved(id) =>
        id should not be 0
        testChemical = testChemical.copy(id = id)
      case _ => fail("Invalid chemical saved event.")    

  def updateChemical: Unit =
    testChemical = testChemical.copy(amount = 2.0)
    val saveChemical = SaveChemical(testAccount.license, testChemical)
    dispatcher.dispatch(saveChemical) match
      case ChemicalSaved(id) => id shouldBe testChemical.id
      case _ => fail("Invalid chemical saved event.")

  def listChemicals: Unit =
    val listChemicals = ListChemicals(testAccount.license, testPool.id)
    dispatcher.dispatch(listChemicals) match
      case ChemicalsListed(chemicals) =>
        chemicals.length shouldBe 1
        chemicals.head shouldBe testChemical
      case _ => fail("Invalid chemicals listed event.")