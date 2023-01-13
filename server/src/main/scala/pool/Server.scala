package pool

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import java.net.InetSocketAddress
import java.util.concurrent.Executors

import scala.io.Source

object Server extends LazyLogging:
  private val config = ConfigFactory.load("server.conf")
  private val host = config.getString("host")
  private val port = config.getInt("port")
  private val backlog = config.getInt("backlog")

  private val http = HttpServer.create(InetSocketAddress(port), backlog)
  private val handler = new HttpHandler {
    override def handle(exchange: HttpExchange): Unit =
      val json = Source.fromInputStream( exchange.getRequestBody() ).mkString

      val response = "TODO"
      exchange.sendResponseHeaders(200, response.length())
      exchange.getResponseHeaders().add("Content-Type", "application/json")

      val outputStream = exchange.getResponseBody
      outputStream.write(response.getBytes())
      outputStream.flush()
      outputStream.close()
  }

  @main def main(): Unit =
    http.setExecutor(Executors.newVirtualThreadPerTaskExecutor())
    http.createContext("/command", handler)

    http.start()
    logger.info(s"*** Http Server started at: $host:$port")
    println(s"*** Press Control-C to shutdown server at: $host:$port")

    Thread.currentThread().join()

  sys.addShutdownHook {
    http.stop(10)
    logger.info(s"*** Http Server shutdown at: $host:$port")
    println(s"*** Server shutdown at: $host:$port")
  }