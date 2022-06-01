package pool

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EnumTest extends AnyFunSuite with Matchers:
  test("uom") {
    uom.gl.toString shouldBe "gl"
    uom.kg.toString shouldBe "kg"
    uom.g.toString shouldBe "g"
    uom.l.toString shouldBe "l"
    uom.ml.toString shouldBe "ml"
    uom.lbs.toString shouldBe "lbs"
    uom.oz.toString shouldBe "oz"
  }