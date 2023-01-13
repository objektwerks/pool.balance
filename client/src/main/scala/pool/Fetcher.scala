package pool

import java.net.URI
import java.net.http.HttpRequest

final class Fetcher(url: String):
  def call(command: Command,
           handler: Event => Unit): Unit =
    val request = HttpRequest.newBuilder().uri(new URI(url))
  