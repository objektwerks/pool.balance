package pool

final class Model(context: Context):
  val store = context.store

  def pools(): List[Pool] = store.pools()
  def add(pool: Pool): Int = store.add(pool)
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

  def pHs(): List[pH] = List[pH]()
  def add(pH: pH): Int = 0
  def update(pH: pH): Unit = ()

  def calciumHardnesses(): List[CalciumHardness] = List[CalciumHardness]()
  def add(calciumHardness: CalciumHardness): Int = 0
  def update(calciumHardness: CalciumHardness): Unit = ()

  def totalAlkalinities(): List[TotalAlkalinity] = List[TotalAlkalinity]()
  def add(totalAlkalinity: TotalAlkalinity): Int = 0
  def update(totalAlkalinity: TotalAlkalinity): Unit = ()

  def cyanuricAcids(): List[CyanuricAcid] = List[CyanuricAcid]()
  def add(cyanuricAcid: CyanuricAcid): Int = 0
  def update(cyanuricAcid: CyanuricAcid): Unit = ()

  def totalBromines(): List[TotalBromine] = List[TotalBromine]()
  def add(totalBromine: TotalBromine): Int = 0
  def update(totalBromine: TotalBromine): Unit = ()

  def temperatures(): List[Temperature] = List[Temperature]()
  def add(temperature: Temperature): Int = 0
  def update(temperature: Temperature): Unit = ()