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
  def listPools(): List[Pool] = List[Pool]()

  def listFreeChlorines(): List[FreeChlorine] = List[FreeChlorine]()

  def listCombinedChlorines(): List[CombinedChlorine] = List[CombinedChlorine]()

  def listTotalChlorines(): List[TotalChlorine] = List[TotalChlorine]()

  def listPhs(): List[pH] = List[pH]()

  