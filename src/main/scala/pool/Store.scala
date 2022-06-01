package pool

import scalikejdbc.*

final class Store(context: Context):
  ConnectionPool.singleton(DataSourceConnectionPool(context.dataSource))

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

  def measurements(): List[Measurement] = DB readOnly { implicit session =>
    sql"select * from measurement order by date_measured, time_measured"
      .map(rs => Measurement(
        rs.long("id"),
        rs.long("pool_id"),
        typeofMeasurement.valueOf( rs.string("typeof") ),
        rs.localDate("date_measured"),
        rs.localTime("time_measured"), 
        rs.double("measurement"))
      )
      .list()
  }

  def add(measurement: Measurement): Measurement = DB localTx { implicit session =>
    val id = sql"""
      insert into measurement(pool_id, typeof, date_measured, time_measured, measurement)
      values(${measurement.poolId}, ${measurement.typeof.toString}, ${measurement.dateMeasured},
      ${measurement.timeMeasured}, ${measurement.measurement})
      """
      .updateAndReturnGeneratedKey()
    measurement.copy(id = id)
  }

  def update(measurement: Measurement): Unit = DB localTx { implicit session =>
    sql"""
      update measurement set date_measured = ${measurement.dateMeasured}, time_measured = ${measurement.timeMeasured},
      measurement = ${measurement.measurement} where id = ${measurement.id}
      """
      .update()
  }

  def chemicals(): List[Chemical] = DB readOnly { implicit session =>
    sql"select * from measurement order by date_added, time_added"
      .map(rs => Chemical(
        rs.long("id"),
        rs.long("pool_id"),
        typeofChemical.valueOf( rs.string("typeof") ),
        rs.localDate("date_added"),
        rs.localTime("time_added"), 
        rs.double("amount"),
        uom.valueOf( rs.string("unit") ))
      )
      .list()
  }

  def add(chemical: Chemical): Chemical = DB localTx { implicit session =>
    val id = sql"""
      insert into chemical(pool_id, typeof, date_added, time_added, amouont, unit)
      values(${chemical.poolId}, ${chemical.typeof.toString}, ${chemical.dateAdded},
      ${chemical.timeAdded}, ${chemical.amount}, ${chemical.unit.toString})
      """
      .updateAndReturnGeneratedKey()
    chemical.copy(id = id)
  }

  def update(chemical: Chemical): Unit = DB localTx { implicit session =>
    sql"""
      update chemical set date_added = ${chemical.dateAdded}, time_added = ${chemical.timeAdded},
      amount = ${chemical.amount}, unit = ${chemical.unit.toString} where id = ${chemical.id}
      """
      .update()
  }