package objektwerks

import com.typesafe.config.ConfigFactory

object Conf:
  private val conf = ConfigFactory.load("app.conf")
  val windowTitle = conf.getString("window.title")
  val windowWidth = conf.getDouble("window.width")
  val windowHeight = conf.getDouble("window.height")