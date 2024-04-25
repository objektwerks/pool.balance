package pool

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import io.helidon.webserver.WebServer
import io.helidon.webserver.http.HttpRouting

import scala.concurrent.duration.DurationInt

object Server extends LazyLogging:
  @main def main(): Unit =
    val config = ConfigFactory.load("server.conf")
    val host = config.getString("server.host")
    val port = config.getInt("server.port")
    val endpoint = config.getString("server.endpoint")

    val store = Store(config, Store.cache(minSize = 4, maxSize = 10, expireAfter = 24.hour))
    val emailer = Emailer(config)
    val dispatcher = Dispatcher(store, emailer)

    val handler = Handler(dispatcher, store, logger)

    val builder = HttpRouting
      .builder
      .post(endpoint, handler)

    WebServer
      .builder
      .port(port)
      .routing(builder)
      .build
      .start

    println(s"*** Press Control-C to shutdown Pool Balance Http Server at: $host:$port$endpoint")
    logger.info(s"*** Pool Balance Http Server started at: $host:$port$endpoint")

    Thread.currentThread().join()