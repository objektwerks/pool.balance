package pool

final class Model(context: Context):
  val store = context.store

  def pools(): List[Pool] = store.pools()
  def add(pool: Pool): Pool = store.add(pool)
  def update(pool: Pool): Unit = store.update(pool)

  def freeChlorines(): List[FreeChlorine] = store.freeChlorines()
  def add(freeChlorine: FreeChlorine): Int = store.add(freeChlorine)
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