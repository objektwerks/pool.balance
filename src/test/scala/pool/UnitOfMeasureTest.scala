package pool

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class UnitOfMeasureTest extends AnyFunSuite with Matchers:
  test("unit of measure") {
    UnitOfMeasure.valueOf(UnitOfMeasure.gl.toString) shouldBe UnitOfMeasure.gl
    UnitOfMeasure.valueOf(UnitOfMeasure.l.toString) shouldBe UnitOfMeasure.l
    UnitOfMeasure.valueOf(UnitOfMeasure.kg.toString) shouldBe UnitOfMeasure.kg
    UnitOfMeasure.valueOf(UnitOfMeasure.lb.toString) shouldBe UnitOfMeasure.lb
    UnitOfMeasure.valueOf(UnitOfMeasure.tablet.toString) shouldBe UnitOfMeasure.tablet
  }

  test("gallons to liters") {

  }

  test("liters to gallons") {

  }

  test("pounds to kilograms") {

  }

  test("kilograms to pounds") {
    
  }