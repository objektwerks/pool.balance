package pool

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EnumTest extends AnyFunSuite with Matchers:
  test("unit of measure") {
    UnitOfMeasure.valueOf(UnitOfMeasure.gl.toString) shouldBe UnitOfMeasure.gl
    UnitOfMeasure.valueOf(UnitOfMeasure.kg.toString) shouldBe UnitOfMeasure.kg
    UnitOfMeasure.valueOf(UnitOfMeasure.g.toString) shouldBe UnitOfMeasure.g
    UnitOfMeasure.valueOf(UnitOfMeasure.l.toString) shouldBe UnitOfMeasure.l
    UnitOfMeasure.valueOf(UnitOfMeasure.ml.toString) shouldBe UnitOfMeasure.ml
    UnitOfMeasure.valueOf(UnitOfMeasure.lbs.toString) shouldBe UnitOfMeasure.lbs
    UnitOfMeasure.valueOf(UnitOfMeasure.oz.toString) shouldBe UnitOfMeasure.oz
  }

  test("type of chemical") {
    typeOfChemical.valueOf(typeOfChemical.liquidChlorine.toString) shouldBe typeOfChemical.liquidChlorine
    typeOfChemical.valueOf(typeOfChemical.trichlor.toString) shouldBe typeOfChemical.trichlor
    typeOfChemical.valueOf(typeOfChemical.dichlor.toString) shouldBe typeOfChemical.dichlor
    typeOfChemical.valueOf(typeOfChemical.calciumHypochlorite.toString) shouldBe typeOfChemical.calciumHypochlorite
    typeOfChemical.valueOf(typeOfChemical.stabilizer.toString) shouldBe typeOfChemical.stabilizer
    typeOfChemical.valueOf(typeOfChemical.algaecide.toString) shouldBe typeOfChemical.algaecide
  }