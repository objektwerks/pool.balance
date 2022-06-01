package pool

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EnumTest extends AnyFunSuite with Matchers:
  test("uom") {
    uom.valueOf(uom.gl.toString) shouldBe uom.gl
    uom.valueOf(uom.kg.toString) shouldBe uom.kg
    uom.valueOf(uom.g.toString) shouldBe uom.g
    uom.valueOf(uom.l.toString) shouldBe uom.l
    uom.valueOf(uom.ml.toString) shouldBe uom.ml
    uom.valueOf(uom.lbs.toString) shouldBe uom.lbs
    uom.valueOf(uom.oz.toString) shouldBe uom.oz
 }