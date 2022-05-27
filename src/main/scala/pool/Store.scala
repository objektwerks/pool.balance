package pool

import scalikejdbc.*

final class Store(conf: Conf):
  ConnectionPool.singleton(conf.url, conf.user, conf.password)

  def ping: Boolean = true