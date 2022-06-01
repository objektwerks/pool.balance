package pool

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EnumTest extends AnyFunSuite with Matchers:
  test("uom") {
    unitOfMeasure.valueOf(unitOfMeasure.gl.toString) shouldBe unitOfMeasure.gl
    unitOfMeasure.valueOf(unitOfMeasure.kg.toString) shouldBe unitOfMeasure.kg
    unitOfMeasure.valueOf(unitOfMeasure.g.toString) shouldBe unitOfMeasure.g
    unitOfMeasure.valueOf(unitOfMeasure.l.toString) shouldBe unitOfMeasure.l
    unitOfMeasure.valueOf(unitOfMeasure.ml.toString) shouldBe unitOfMeasure.ml
    unitOfMeasure.valueOf(unitOfMeasure.lbs.toString) shouldBe unitOfMeasure.lbs
    unitOfMeasure.valueOf(unitOfMeasure.oz.toString) shouldBe unitOfMeasure.oz
    unitOfMeasure.list shouldBe Array("gl", "kg", "g", "l", "ml", "lbs", "oz")
 }