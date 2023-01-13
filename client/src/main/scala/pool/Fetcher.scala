package pool

import com.github.plokhotnyuk.jsoniter_scala.core.*

import java.net.URI
import java.net.http.HttpRequest

import Serializer.given

final class Fetcher(url: String):
  def call(command: Command,
           handler: Event => Unit): Unit =
    val request = HttpRequest.newBuilder().uri(new URI(url))
  