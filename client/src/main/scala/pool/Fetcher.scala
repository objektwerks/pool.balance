package pool

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.typesafe.scalalogging.LazyLogging

import io.helidon.webclient.api.WebClient

import scalafx.application.Platform

import scala.util.Try
import scala.util.control.NonFatal

import Serializer.given

final class Fetcher(context: Context) extends LazyLogging:
  val url = context.url
  val endpoint = context.endpoint
  val connectError = context.errorServer
  val client = WebClient
    .builder
    .baseUri(url)
    .addHeader("Content-Type", "application/json; charset=UTF-8")
    .addHeader("Accept", "application/json")
    .build

  logger.info(s"*** Fetcher url: $url endpoint: $endpoint")

  def fetch(command: Command,
            handler: Event => Unit): Unit =
    logger.info(s"*** Fetcher command: $command")
    val commandJson = writeToString[Command](command)
    Try {
      val eventJson = client
        .post(endpoint)
        .submit(commandJson, classOf[String])
        .entity
      val event = readFromString[Event](eventJson)
      logger.info(s"*** Fetcher event: $event")
      Platform.runLater(handler(event))
    }.recover {
      case NonFatal(throwable: Throwable) =>
        val fault = Fault(
          if throwable.getMessage == null then connectError
          else throwable.getMessage
        )
        logger.error(s"Fetcher fault: $fault")
        Platform.runLater(handler(fault))
    }