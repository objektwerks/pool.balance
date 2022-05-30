package pool

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

final class StoreTest extends AnyFunSuite with Matchers:
  val store = Context( ConfigFactory.load("test.conf") ).store

  test("store") {
    store.pools().isEmpty shouldBe true
  }