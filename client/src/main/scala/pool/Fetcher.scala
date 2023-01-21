package pool

import com.github.plokhotnyuk.jsoniter_scala.core.*

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration
import java.time.temporal.ChronoUnit.SECONDS
import java.util.concurrent.Executors

import scalafx.application.Platform
import scala.concurrent.{blocking, Await, ExecutionContext, Future}
import scala.concurrent.duration.*

import Serializer.given

final class Fetcher(url: String):
  implicit private val executionContext: ExecutionContext = ExecutionContext.fromExecutor( Executors.newVirtualThreadPerTaskExecutor() )

  private val uri = URI(url)
  private val client = HttpClient
                         .newBuilder
                         .executor( Executors.newVirtualThreadPerTaskExecutor() )
                         .build

  private def fromCommandToJson(command: Command): String = writeToString[Command](command)

  private def fromJsonToEvent(json: String): Event = readFromString[Event](json)

  private def buildHttpRequest(json: String): HttpRequest =
    HttpRequest
          .newBuilder
          .uri(uri)
          .timeout(Duration.of(30, SECONDS))
          .version(HttpClient.Version.HTTP_2)
          .headers("Content-Type", "application/json; charset=UTF-8", "Accept", "application/json")
          .POST( HttpRequest.BodyPublishers.ofString(json) )
          .build

  private def sendAsyncHttpRequest(httpRequest: HttpRequest): HttpResponse[String] =
    val future = Future {
      blocking {
        require(!Platform.isFxApplicationThread, "Http client should not send request in fx thread.")
        client.send( httpRequest, BodyHandlers.ofString )
      }
    }
    Await.result( future, 30.seconds )

  def call(command: Command,
           handler: Event => Unit): Any =
    val commandJson = fromCommandToJson(command)
    val httpRequest = buildHttpRequest(commandJson)

    val httpResponse = sendAsyncHttpRequest(httpRequest)
    val eventJson = httpResponse.body 
    val event = fromJsonToEvent(eventJson)

    handler(event)