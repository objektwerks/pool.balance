package pool

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import UnitOfMeasure.*

class UnitOfMeasureTest extends AnyFunSuite with Matchers:
  test("unit of measure") {
    UnitOfMeasure.valueOf(UnitOfMeasure.gl.toString) shouldBe UnitOfMeasure.gl
    UnitOfMeasure.valueOf(UnitOfMeasure.l.toString) shouldBe UnitOfMeasure.l
    UnitOfMeasure.valueOf(UnitOfMeasure.kg.toString) shouldBe UnitOfMeasure.kg
    UnitOfMeasure.valueOf(UnitOfMeasure.lb.toString) shouldBe UnitOfMeasure.lb
    UnitOfMeasure.valueOf(UnitOfMeasure.tablet.toString) shouldBe UnitOfMeasure.tablet
  }

  test("gallons to liters") {
    gallonsToLiters(1.0) should be > 0.0
  }

  test("liters to gallons") {
    litersToGallons(1.0) should be > 0.0
  }

  test("pounds to kilograms") {
    poundsToKilograms(1.0) should be > 0.0
  }

  test("kilograms to pounds") {
    kilogramsToPounds(1.0) should be > 0.0
  }