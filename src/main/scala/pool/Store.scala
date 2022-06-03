package pool

import java.time.LocalTime

import scalikejdbc.*

import Entity.*

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

  def cleanings(): List[Cleaning] = DB readOnly { implicit session =>
    sql"select * from cleaning order by date_cleaned"
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

  def measurements(typeof: typeOfMeasurement): List[Measurement] = DB readOnly { implicit session =>
    sql"select * from measurement where typeof = ${typeof.toString} order by date_measured, time_measured"
      .map(rs => Measurement(
        rs.long("id"),
        rs.long("pool_id"),
        typeOfMeasurement.valueOf( rs.string("typeof") ),
        rs.localDate("date_measured"),
        formatLocalTime( rs.localTime("time_measured") ),
        rs.double("measurement"))
      )
      .list()
  }
  def add(measurement: Measurement): Measurement = DB localTx { implicit session =>
    val id = sql"""
      insert into measurement(pool_id, typeof, date_measured, time_measured, measurement)
      values(${measurement.poolId}, ${measurement.typeof.toString}, ${measurement.dateMeasured},
      ${formatLocalTime(measurement.timeMeasured)}, ${measurement.measurement})
      """
      .updateAndReturnGeneratedKey()
    measurement.copy(id = id)
  }
  def update(measurement: Measurement): Unit = DB localTx { implicit session =>
    sql"""
      update measurement set date_measured = ${measurement.dateMeasured},
      time_measured = ${formatLocalTime(measurement.timeMeasured)},
      measurement = ${measurement.measurement} where id = ${measurement.id}
      """
      .update()
  }

  def chemicals(typeof: typeOfChemical): List[Chemical] = DB readOnly { implicit session =>
    sql"select * from chemical where typeof = ${typeof.toString} order by date_added, time_added"
      .map(rs => Chemical(
        rs.long("id"),
        rs.long("pool_id"),
        typeOfChemical.valueOf( rs.string("typeof") ),
        rs.localDate("date_added"),
        formatLocalTime( rs.localTime("time_added") ),
        rs.double("amount"),
        unitOfMeasure.valueOf( rs.string("unit") ))
      )
      .list()
  }
  def add(chemical: Chemical): Chemical = DB localTx { implicit session =>
    val id = sql"""
      insert into chemical(pool_id, typeof, date_added, time_added, amount, unit)
      values(${chemical.poolId}, ${chemical.typeof.toString}, ${chemical.dateAdded},
      ${formatLocalTime(chemical.timeAdded)}, ${chemical.amount}, ${chemical.unit.toString})
      """
      .updateAndReturnGeneratedKey()
    chemical.copy(id = id)
  }
  def update(chemical: Chemical): Unit = DB localTx { implicit session =>
    sql"""
      update chemical set date_added = ${chemical.dateAdded}, time_added = ${formatLocalTime(chemical.timeAdded)},
      amount = ${chemical.amount}, unit = ${chemical.unit.toString} where id = ${chemical.id}
      """
      .update()
  }