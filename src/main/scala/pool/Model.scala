package pool

final class Model(context: Context):
  def pools(): List[Pool] = List[Pool]()
  def add(pool: Pool): Int = 0
  def update(pool: Pool): Unit = ()

  def freeChlorines(): List[FreeChlorine] = List[FreeChlorine]()
  def add(freeChlorine: FreeChlorine): Int = 0
  def update(freeChlorine: FreeChlorine): Unit = ()

  def combinedChlorines(): List[CombinedChlorine] = List[CombinedChlorine]()
  def add(combinedChlorine: CombinedChlorine): Int = 0
  def update(combinedChlorine: CombinedChlorine): Unit = ()

  def totalChlorines(): List[TotalChlorine] = List[TotalChlorine]()
  def add(totalChlorine: TotalChlorine): Int = 0
  def update(totalChlorine: TotalChlorine): Unit = ()

  def pHs(): List[pH] = List[pH]()

  def calciumHardnesses(): List[CalciumHardness] = List[CalciumHardness]()

  def totalAlkalinities(): List[TotalAlkalinity] = List[TotalAlkalinity]()

  def cyanuricAcids(): List[CyanuricAcid] = List[CyanuricAcid]()

  def totalBromines(): List[TotalBromine] = List[TotalBromine]()

  def temperatures(): List[Temperature] = List[Temperature]()