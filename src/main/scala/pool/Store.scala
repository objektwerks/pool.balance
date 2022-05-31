package pool

import com.zaxxer.hikari.HikariDataSource

import javax.sql.DataSource

import scalikejdbc.*

final class Store(context: Context):
  private val dataSource: DataSource = {
    val ds = new HikariDataSource()
    ds.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource")
    ds.addDataSourceProperty("url", context.url)
    ds.addDataSourceProperty("user", context.user)
    ds.addDataSourceProperty("password", context.password)
    ds
  }
  ConnectionPool.singleton(DataSourceConnectionPool(dataSource))

  def pools(): List[Pool] = DB readOnly { implicit session =>
    sql"select * from pool order by built desc"
      .map(rs => Pool(rs.long("id"), rs.string("name"), rs.localDate("built"), rs.int("volume")))
      .list()
  }

  def add(pool: Pool): Pool = DB localTx { implicit session =>
    val id = sql"insert into pool(name, built, volume) values(${pool.name}, ${pool.built}, ${pool.volume})"
      .updateAndReturnGeneratedKey()
    pool.copy(id = id)
  }

  def update(pool: Pool): Unit = DB localTx { implicit session =>
    sql"update pool set name = ${pool.name}, built = ${pool.built}, volume = ${pool.volume} where id = ${pool.id}"
      .update()
  }

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

  def liquidChlorines(): List[LiquidChlorine] = List[LiquidChlorine]()
  def add(liquidChlorine: LiquidChlorine): Int = 0
  def update(liquidChlorine: LiquidChlorine): Unit = ()

  def trichlors(): List[Trichlor] = List[Trichlor]()
  def add(trichlor: Trichlor): Int = 0
  def update(trichlor: Trichlor): Unit = ()

  def dichlors(): List[Dichlor] = List[Dichlor]()
  def add(dichlor: Dichlor): Int = 0
  def update(dichlor: Dichlor): Unit = ()

  def calciumHypochlorites(): List[CalciumHypochlorite] = List[CalciumHypochlorite]()
  def add(calciumHypochlorite: CalciumHypochlorite): Int = 0
  def update(calciumHypochlorite: CalciumHypochlorite): Unit = ()

  def stabilizers(): List[Stabilizer] = List[Stabilizer]()
  def add(stabilizer: Stabilizer): Int = 0
  def update(stabilizer: Stabilizer): Unit = ()