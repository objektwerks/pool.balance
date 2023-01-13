package pool

import com.github.plokhotnyuk.jsoniter_scala.core.*

import java.net.URI
import java.net.http.{HttpClient, HttpRequest}

import Serializer.given

final class Fetcher(url: String):
  def call(command: Command,
           handler: Event => Unit): Unit =
    val commandJson = writeToString[Command](command)
    val request = HttpRequest
      .newBuilder()
      .uri(URI(url))
      .version(HttpClient.Version.HTTP_2)
      .headers("Content-Type", "application/json; charset=UTF-8", "Accept", "application/json")
      .POST(commandJson)
      .build()
  