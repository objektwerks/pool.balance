package pool

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EnumTest extends AnyFunSuite with Matchers:
  test("unit of measure") {
    unitOfMeasure.valueOf(unitOfMeasure.gl.toString) shouldBe unitOfMeasure.gl
    unitOfMeasure.valueOf(unitOfMeasure.kg.toString) shouldBe unitOfMeasure.kg
    unitOfMeasure.valueOf(unitOfMeasure.g.toString) shouldBe unitOfMeasure.g
    unitOfMeasure.valueOf(unitOfMeasure.l.toString) shouldBe unitOfMeasure.l
    unitOfMeasure.valueOf(unitOfMeasure.ml.toString) shouldBe unitOfMeasure.ml
    unitOfMeasure.valueOf(unitOfMeasure.lbs.toString) shouldBe unitOfMeasure.lbs
    unitOfMeasure.valueOf(unitOfMeasure.oz.toString) shouldBe unitOfMeasure.oz
  }

  test("type of chemical") {
    typeOfChemical.valueOf(typeOfChemical.liquidChlorine.toString) shouldBe typeOfChemical.liquidChlorine
    typeOfChemical.valueOf(typeOfChemical.trichlor.toString) shouldBe typeOfChemical.trichlor
    typeOfChemical.valueOf(typeOfChemical.dichlor.toString) shouldBe typeOfChemical.dichlor
    typeOfChemical.valueOf(typeOfChemical.calciumHypochlorite.toString) shouldBe typeOfChemical.calciumHypochlorite
    typeOfChemical.valueOf(typeOfChemical.stabilizer.toString) shouldBe typeOfChemical.stabilizer
    typeOfChemical.valueOf(typeOfChemical.algaecide.toString) shouldBe typeOfChemical.algaecide
  }