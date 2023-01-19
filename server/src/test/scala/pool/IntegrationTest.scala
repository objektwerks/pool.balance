package pool

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.*
import scala.sys.process.Process

class IntegrationTest extends AnyFunSuite with Matchers:
  val exitCode = Process("psql -d poolbalance -f ddl.sql").run().exitValue()

  val config = ConfigFactory.load("test.conf")

  val store = Store(config, Store.cache(minSize = 4, maxSize = 10, expireAfter = 24.hour))
  val emailer = Emailer(config)
  val dispatcher = Dispatcher(store, emailer)

  test("integration") {
    
  }