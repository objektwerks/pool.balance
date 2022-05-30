package pool

/*
2. FreeChlorine
3. CombinedChlorine
4. TotalChlorine
5. pH
6. CalciumHardness
7. TotalAlkalinity
8. CyanuricAcid
9. TotalBromine
10. Temperature
*/
final class Model(context: Context):
  def pools(): List[Pool] = List[Pool]()

  def freeChlorines(): List[FreeChlorine] = List[FreeChlorine]()

  def combinedChlorines(): List[CombinedChlorine] = List[CombinedChlorine]()

  def totalChlorines(): List[TotalChlorine] = List[TotalChlorine]()

  def pHs(): List[pH] = List[pH]()

  def calciumHardnesses(): List[CalciumHardness] = List[CalciumHardness]()

  def TotalAlkalinities(): List[TotalAlkalinity] = List[TotalAlkalinity]()

  