package pool

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import java.time.LocalDate

final class StoreTest extends AnyFunSuite with Matchers:
  val store = Context( ConfigFactory.load("test.conf") ).store

  test("pool") {
    val pool = Pool(name = "pool-a", built = LocalDate.now(), volume = 10000)

    val added = store.add(pool)
    added.id should not be 0

    val updated = added.copy(volume = 11000)
    store.update(updated)

    val list = store.pools()
    list.length shouldBe 1
    list.head shouldBe updated
  }