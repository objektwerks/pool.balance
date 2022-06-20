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
    TypeOfChemical.valueOf(TypeOfChemical.LiquidChlorine.toString) shouldBe TypeOfChemical.LiquidChlorine
    TypeOfChemical.valueOf(TypeOfChemical.Trichlor.toString) shouldBe TypeOfChemical.Trichlor
    TypeOfChemical.valueOf(TypeOfChemical.Dichlor.toString) shouldBe TypeOfChemical.Dichlor
    TypeOfChemical.valueOf(TypeOfChemical.CalciumHypochlorite.toString) shouldBe TypeOfChemical.CalciumHypochlorite
    TypeOfChemical.valueOf(TypeOfChemical.Stabilizer.toString) shouldBe TypeOfChemical.Stabilizer
    TypeOfChemical.valueOf(TypeOfChemical.Algaecide.toString) shouldBe TypeOfChemical.Algaecide
  }