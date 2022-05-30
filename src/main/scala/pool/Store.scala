package pool

import scalikejdbc.*

final class Store(context: Context):
  ConnectionPool.singleton(context.url, context.user, context.password)

  def pools(): List[Pool] =
    DB readOnly { implicit session =>
      sql"select * from pool order by built desc"
        .map(rs => Pool(rs.int("id"), rs.string("name"), rs.localDate("built"), rs.int("volume")))
        .list()
    }

  def add(pool: Pool): Pool =
    val id = DB localTx { implicit session =>
      sql"insert into pool(id, name, built, volume) values(${pool.id}, ${pool.name}, ${pool.built}, ${pool.volume})"
      .updateAndReturnGeneratedKey()
    }
    pool.copy(id = id)

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