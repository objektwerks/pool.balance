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
    TypeOfChemical.valueOf(TypeOfChemical.liquidChlorine.toString) shouldBe TypeOfChemical.liquidChlorine
    TypeOfChemical.valueOf(TypeOfChemical.trichlor.toString) shouldBe TypeOfChemical.trichlor
    TypeOfChemical.valueOf(TypeOfChemical.dichlor.toString) shouldBe TypeOfChemical.dichlor
    TypeOfChemical.valueOf(TypeOfChemical.calciumHypochlorite.toString) shouldBe TypeOfChemical.calciumHypochlorite
    TypeOfChemical.valueOf(TypeOfChemical.stabilizer.toString) shouldBe TypeOfChemical.stabilizer
    TypeOfChemical.valueOf(TypeOfChemical.algaecide.toString) shouldBe TypeOfChemical.algaecide
  }