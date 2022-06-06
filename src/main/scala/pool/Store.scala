package pool

import scalikejdbc.*

final class Store(context: Context):
  ConnectionPool.singleton(DataSourceConnectionPool(context.dataSource))

  def pools(): List[Pool] = DB readOnly { implicit session =>
    sql"select * from pool order by built desc"
      .map(rs => Pool(
        rs.long("id"),
        rs.string("name"), 
        rs.localDate("built"), 
        rs.int("volume"), 
        unitOfMeasure.valueOf( rs.string("unit") ))
      )
      .list()
  }

  def add(pool: Pool): Pool = DB localTx { implicit session =>
    val id = sql"""
      insert into pool(name, built, volume, unit) values(${pool.name}, ${pool.built}, ${pool.volume}, ${pool.unit.toString})
      """
      .updateAndReturnGeneratedKey()
    pool.copy(id = id)
  }

  def update(pool: Pool): Unit = DB localTx { implicit session =>
    sql"""
      update pool set name = ${pool.name}, built = ${pool.built}, volume = ${pool.volume}, unit = ${pool.unit.toString}
      where id = ${pool.id}
      """
      .update()
  }

  def cleanings(poolId: Long): List[Cleaning] = DB readOnly { implicit session =>
    sql"select * from cleaning where pool_id = ${poolId} order by date_cleaned desc"
      .map(rs => Cleaning(
        rs.long("id"),
        rs.long("pool_id"),
        rs.boolean("brush"),
        rs.boolean("net"),
        rs.boolean("skimmer_basket"),
        rs.boolean("pump_basket"),
        rs.boolean("pump_filter"),
        rs.boolean("vacuum"),
        rs.localDate("date_cleaned"))
      )
      .list()
  }

  def add(cleaning: Cleaning): Cleaning = DB localTx { implicit session =>
    val id = sql"""
      insert into cleaning(pool_id, brush, net, skimmer_basket, pump_basket, pump_filter, vacuum, date_cleaned)
      values(${cleaning.poolId}, ${cleaning.brush}, ${cleaning.net}, ${cleaning.skimmerBasket},
      ${cleaning.pumpBasket}, ${cleaning.pumpFilter}, ${cleaning.vacuum}, ${cleaning.dateCleaned})
      """
      .updateAndReturnGeneratedKey()
    cleaning.copy(id = id)
  }

  def update(cleaning: Cleaning): Unit = DB localTx { implicit session =>
    sql"""
      update cleaning set brush = ${cleaning.brush}, net = ${cleaning.net}, skimmer_basket = ${cleaning.skimmerBasket},
      pump_basket = ${cleaning.pumpBasket}, pump_filter = ${cleaning.pumpFilter}, vacuum = ${cleaning.vacuum},
      date_cleaned = ${cleaning.dateCleaned} where id = ${cleaning.id}
      """
      .update()
  }

  def measurements(poolId: Long): List[Measurement] = DB readOnly { implicit session =>
    sql"select * from measurement where pool_id = ${poolId} order by date_measured, time_measured desc"
      .map(rs => Measurement(
        rs.long("id"),
        rs.long("pool_id"),
        rs.int("free_chlorine"),
        rs.double("combined_chlorine"),
        rs.int("total_chlorine"),
        rs.double("ph"),
        rs.int("calcium_hardness"),
        rs.int("total_alkalinity"),
        rs.int("cyanuric_acid"),
        rs.int("total_bromine"),
        rs.int("temperature"),
        rs.localDate("date_measured"),
        rs.localTime("time_measured"))
      )
      .list()
  }

  def add(measurement: Measurement): Measurement = DB localTx { implicit session =>
    val id = sql"""
      insert into measurement(pool_id, free_chlorine, combined_chlorine, total_chlorine, ph, calcium_hardness,
      total_alkalinity, cyanuric_acid, total_bromine, temperature, date_measured, time_measured)
      values(${measurement.poolId}, ${measurement.freeChlorine}, ${measurement.combinedChlorine}, ${measurement.totalChlorine},
      ${measurement.ph}, ${measurement.calciumHardness}, ${measurement.totalAlkalinity}, ${measurement.cyanuricAcid},
      ${measurement.totalBromine}, ${measurement.temperature}, ${measurement.dateMeasured}, ${measurement.timeMeasured})
      """
      .updateAndReturnGeneratedKey()
    measurement.copy(id = id)
  }

  def update(measurement: Measurement): Unit = DB localTx { implicit session =>
    sql"""
      update measurement set free_chlorine = ${measurement.freeChlorine}, combined_chlorine = ${measurement.combinedChlorine},
      total_chlorine = ${measurement.totalChlorine}, ph = ${measurement.ph}, calcium_hardness = ${measurement.calciumHardness},
      total_alkalinity = ${measurement.totalAlkalinity}, cyanuric_acid = ${measurement.cyanuricAcid},
      total_bromine = ${measurement.totalBromine}, temperature = ${measurement.temperature},
      date_measured = ${measurement.dateMeasured}, time_measured = ${measurement.timeMeasured}
       where id = ${measurement.id}
      """
      .update()
  }

  def chemicals(poolId: Long, typeof: typeOfChemical): List[Chemical] = DB readOnly { implicit session =>
    sql"select * from chemical where pool_id = ${poolId} and typeof = ${typeof.toString} order by date_added, time_added desc"
      .map(rs => Chemical(
        rs.long("id"),
        rs.long("pool_id"),
        typeOfChemical.valueOf( rs.string("typeof") ),
        rs.double("amount"),
        unitOfMeasure.valueOf( rs.string("unit") ),
        rs.localDate("date_added"),
        rs.localTime("time_added"))
      )
      .list()
  }

  def add(chemical: Chemical): Chemical = DB localTx { implicit session =>
    val id = sql"""
      insert into chemical(pool_id, typeof, amount, unit, date_added, time_added)
      values(${chemical.poolId}, ${chemical.typeof.toString}, ${chemical.amount}, ${chemical.unit.toString},
      ${chemical.dateAdded}, ${chemical.timeAdded})
      """
      .updateAndReturnGeneratedKey()
    chemical.copy(id = id)
  }

  def update(chemical: Chemical): Unit = DB localTx { implicit session =>
    sql"""
      update chemical set typeof = ${chemical.typeof.toString}, amount = ${chemical.amount},
      unit = ${chemical.unit.toString}, date_added = ${chemical.dateAdded}, time_added = ${chemical.timeAdded}
      where id = ${chemical.id}
      """
      .update()
  }