package pool

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class TypeOfChemicalTest extends AnyFunSuite with Matchers:
  test("unit of measure") {
    UnitOfMeasure.valueOf(UnitOfMeasure.gl.toString) shouldBe UnitOfMeasure.gl
    UnitOfMeasure.valueOf(UnitOfMeasure.l.toString) shouldBe UnitOfMeasure.l
    UnitOfMeasure.valueOf(UnitOfMeasure.kg.toString) shouldBe UnitOfMeasure.kg
    UnitOfMeasure.valueOf(UnitOfMeasure.lb.toString) shouldBe UnitOfMeasure.lb
    UnitOfMeasure.valueOf(UnitOfMeasure.tablet.toString) shouldBe UnitOfMeasure.tablet
  }

  test("type of chemical") {
    TypeOfChemical.valueOf(TypeOfChemical.LiquidChlorine.toString) shouldBe TypeOfChemical.LiquidChlorine
    TypeOfChemical.valueOf(TypeOfChemical.Trichlor.toString) shouldBe TypeOfChemical.Trichlor
    TypeOfChemical.valueOf(TypeOfChemical.Dichlor.toString) shouldBe TypeOfChemical.Dichlor
    TypeOfChemical.valueOf(TypeOfChemical.CalciumHypochlorite.toString) shouldBe TypeOfChemical.CalciumHypochlorite
    TypeOfChemical.valueOf(TypeOfChemical.Stabilizer.toString) shouldBe TypeOfChemical.Stabilizer
    TypeOfChemical.valueOf(TypeOfChemical.Algaecide.toString) shouldBe TypeOfChemical.Algaecide
    TypeOfChemical.valueOf(TypeOfChemical.MuriaticAcid.toString) shouldBe TypeOfChemical.MuriaticAcid
    TypeOfChemical.valueOf(TypeOfChemical.Salt.toString) shouldBe TypeOfChemical.Salt

    TypeOfChemical.toEnum(TypeOfChemical.LiquidChlorine.display) shouldBe TypeOfChemical.LiquidChlorine
    TypeOfChemical.toEnum(TypeOfChemical.Trichlor.display) shouldBe TypeOfChemical.Trichlor
    TypeOfChemical.toEnum(TypeOfChemical.Dichlor.display) shouldBe TypeOfChemical.Dichlor
    TypeOfChemical.toEnum(TypeOfChemical.CalciumHypochlorite.display) shouldBe TypeOfChemical.CalciumHypochlorite
    TypeOfChemical.toEnum(TypeOfChemical.Stabilizer.display) shouldBe TypeOfChemical.Stabilizer
    TypeOfChemical.toEnum(TypeOfChemical.Algaecide.display) shouldBe TypeOfChemical.Algaecide
    TypeOfChemical.toEnum(TypeOfChemical.MuriaticAcid.display) shouldBe TypeOfChemical.MuriaticAcid
    TypeOfChemical.toEnum(TypeOfChemical.Salt.display) shouldBe TypeOfChemical.Salt
 }