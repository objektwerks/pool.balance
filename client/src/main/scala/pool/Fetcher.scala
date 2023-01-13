package pool

final class Fetcher(url: String):
  def call(command: Command,
           handler: Event => Unit): Unit = ???
  