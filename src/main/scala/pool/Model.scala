package pool

import scala.util.Try

final class Model(context: Context):
  val store = context.store

  def pools(): Either[Throwable, List[Pool]] = Try( store.pools() ).toEither
  def add(pool: Pool): Either[Throwable, Pool] = Try( store.add(pool) ).toEither
  def update(pool: Pool):Either[Throwable, Unit] = Try( store.update(pool) ).toEither

  def freeChlorines(): Either[Throwable, List[FreeChlorine]] = Try( store.freeChlorines() ).toEither
  def add(freeChlorine: FreeChlorine): Either[Throwable, FreeChlorine] = Try( store.add(freeChlorine) ).toEither
  def update(freeChlorine: FreeChlorine): Unit = store.update(freeChlorine)

  def combinedChlorines(): List[CombinedChlorine] = store.combinedChlorines()
  def add(combinedChlorine: CombinedChlorine): Int = store.add(combinedChlorine)
  def update(combinedChlorine: CombinedChlorine): Unit = store.update(combinedChlorine)

  def totalChlorines(): List[TotalChlorine] = store.totalChlorines()
  def add(totalChlorine: TotalChlorine): Int = store.add(totalChlorine)
  def update(totalChlorine: TotalChlorine): Unit = store.update(totalChlorine)

  def pHs(): List[pH] = store.pHs()
  def add(pH: pH): Int = store.add(pH)
  def update(pH: pH): Unit = store.update(pH)

  def calciumHardnesses(): List[CalciumHardness] = store.calciumHardnesses()
  def add(calciumHardness: CalciumHardness): Int = store.add(calciumHardness)
  def update(calciumHardness: CalciumHardness): Unit = store.update(calciumHardness)

  def totalAlkalinities(): List[TotalAlkalinity] = store.totalAlkalinities()
  def add(totalAlkalinity: TotalAlkalinity): Int = store.add(totalAlkalinity)
  def update(totalAlkalinity: TotalAlkalinity): Unit = store.update(totalAlkalinity)

  def cyanuricAcids(): List[CyanuricAcid] = store.cyanuricAcids()
  def add(cyanuricAcid: CyanuricAcid): Int = store.add(cyanuricAcid)
  def update(cyanuricAcid: CyanuricAcid): Unit = store.update(cyanuricAcid)

  def totalBromines(): List[TotalBromine] = store.totalBromines()
  def add(totalBromine: TotalBromine): Int = store.add(totalBromine)
  def update(totalBromine: TotalBromine): Unit = store.update(totalBromine)

  def temperatures(): List[Temperature] = store.temperatures()
  def add(temperature: Temperature): Int = store.add(temperature)
  def update(temperature: Temperature): Unit = store.update(temperature)

  def liquidChlorines(): List[LiquidChlorine] = store.liquidChlorines()
  def add(liquidChlorine: LiquidChlorine): Int = store.add(liquidChlorine)
  def update(liquidChlorine: LiquidChlorine): Unit = store.update(liquidChlorine)

  def trichlors(): List[Trichlor] = store.trichlors()
  def add(trichlor: Trichlor): Int = store.add(trichlor)
  def update(trichlor: Trichlor): Unit = store.update(trichlor)

  def dichlors(): List[Dichlor] = store.dichlors()
  def add(dichlor: Dichlor): Int = store.add(dichlor)
  def update(dichlor: Dichlor): Unit = store.update(dichlor)

  def calciumHypochlorites(): List[CalciumHypochlorite] = store.calciumHypochlorites()
  def add(calciumHypochlorite: CalciumHypochlorite): Int = store.add(calciumHypochlorite)
  def update(calciumHypochlorite: CalciumHypochlorite): Unit = store.update(calciumHypochlorite)

  def stabilizers(): List[Stabilizer] = store.stabilizers()
  def add(stabilizer: Stabilizer): Int = store.add(stabilizer)
  def update(stabilizer: Stabilizer): Unit = store.update(stabilizer)

  def algaecides(): List[Algaecide] = store.algaecides()
  def add(algaecide: Algaecide): Int = store.add(algaecide)
  def update(algaecide: Algaecide): Unit = store.update(algaecide)