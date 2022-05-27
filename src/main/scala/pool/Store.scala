package pool

import scalikejdbc.*

final class Store(context: Context):
  ConnectionPool.singleton(context.url, context.user, context.password)

  def ping: Boolean = true