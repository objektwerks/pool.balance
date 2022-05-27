package pool

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

final class StoreTest extends AnyFunSuite with Matchers:
  val conf = Conf( ConfigFactory.load("test.conf") )
  val store = Store(conf)

  test("store") {
    store.ping shouldBe true
  }