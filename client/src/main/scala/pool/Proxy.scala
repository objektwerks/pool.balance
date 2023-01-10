package pool

object Proxy:
  def pools(): List[Pool] = ???

  def add(pool: Pool): Pool = ???

  def update(pool: Pool): Unit = ???

  def cleanings(poolId: Long): List[Cleaning] = ???

  def add(cleaning: Cleaning): Cleaning = ???

  def update(cleaning: Cleaning): Unit = ???

  def measurements(poolId: Long): List[Measurement] = ???

  def add(measurement: Measurement): Measurement = ???

  def update(measurement: Measurement): Unit = ???

  def chemicals(poolId: Long): List[Chemical] = ???

  def add(chemical: Chemical): Chemical = ???

  def update(chemical: Chemical): Unit = ???