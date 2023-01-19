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

  val store = Store(config, Store.cache(minSize = 1, maxSize = 2, expireAfter = 1.hour))
  val emailer = Emailer(config)
  val dispatcher = Dispatcher(store, emailer)

  var testAccount = Account()
  var testPool = Pool()

  test("integration") {
    register
    login
    deactivate
    reactivate
    addPool
    updatePool
    listPools
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
    