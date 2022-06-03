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
    unitOfMeasure.list shouldBe Array("gl", "kg", "g", "l", "ml", "lbs", "oz")
  }

  test("type of measurement") {
    typeOfMeasurement.valueOf(typeOfMeasurement.freeChlorine.toString) shouldBe typeOfMeasurement.freeChlorine
    typeOfMeasurement.valueOf(typeOfMeasurement.combinedChlorine.toString) shouldBe typeOfMeasurement.combinedChlorine
    typeOfMeasurement.valueOf(typeOfMeasurement.totalChlorine.toString) shouldBe typeOfMeasurement.totalChlorine
    typeOfMeasurement.valueOf(typeOfMeasurement.pH.toString) shouldBe typeOfMeasurement.pH
    typeOfMeasurement.valueOf(typeOfMeasurement.calciumHardness.toString) shouldBe typeOfMeasurement.calciumHardness
    typeOfMeasurement.valueOf(typeOfMeasurement.totalAlkalinity.toString) shouldBe typeOfMeasurement.totalAlkalinity
    typeOfMeasurement.valueOf(typeOfMeasurement.cyanuricAcid.toString) shouldBe typeOfMeasurement.cyanuricAcid
    typeOfMeasurement.valueOf(typeOfMeasurement.totalBromine.toString) shouldBe typeOfMeasurement.totalBromine
    typeOfMeasurement.valueOf(typeOfMeasurement.temperature.toString) shouldBe typeOfMeasurement.temperature
  }

  test("type of chemical") {
    typeOfChemical.valueOf(typeOfChemical.liquidChlorine.toString) shouldBe typeOfChemical.liquidChlorine
    typeOfChemical.valueOf(typeOfChemical.trichlor.toString) shouldBe typeOfChemical.trichlor
    typeOfChemical.valueOf(typeOfChemical.dichlor.toString) shouldBe typeOfChemical.dichlor
    typeOfChemical.valueOf(typeOfChemical.calciumHypochlorite.toString) shouldBe typeOfChemical.calciumHypochlorite
    typeOfChemical.valueOf(typeOfChemical.stabilizer.toString) shouldBe typeOfChemical.stabilizer
    typeOfChemical.valueOf(typeOfChemical.algaecide.toString) shouldBe typeOfChemical.algaecide
  }