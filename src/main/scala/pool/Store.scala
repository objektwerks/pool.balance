package pool

import scalikejdbc.*

import Conf.*

class Store:
  ConnectionPool.singleton(url, user, password)