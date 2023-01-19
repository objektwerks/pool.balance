package pool

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.*
import scala.sys.process.Process

import Validator.*

class IntegrationTest extends AnyFunSuite with Matchers:
  Process("psql -d poolbalance -f ddl.sql").run().exitValue()

  val config = ConfigFactory.load("test.conf")

  val store = Store(config, Store.cache(minSize = 4, maxSize = 10, expireAfter = 24.hour))
  val emailer = Emailer(config)
  val dispatcher = Dispatcher(store, emailer)

  var account = Account()

  test("integration") {
    register
    login
  }

  def register: Unit =
    val register = Register(config.getString("email.sender"))
    dispatcher.dispatch(register) match
      case Registered(account) =>
        assert( account.isActivated )
        this.account = account
      case _ => fail("Invalid registered event.")
    
  def login: Unit =
    val login = Login(account.emailAddress, account.pin)
    dispatcher.dispatch(login) match
      case LoggedIn(account) => account shouldBe this.account
      case _ => fail("Invalid loggedin event.")
    