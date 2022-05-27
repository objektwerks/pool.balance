package pool

import com.typesafe.config.ConfigFactory
import scalafx.scene.image.{Image, ImageView}

object Conf:
  private val conf = ConfigFactory.load("app.conf")

  val windowTitle = conf.getString("window.title")
  val windowWidth = conf.getDouble("window.width")
  val windowHeight = conf.getDouble("window.height")

  val url = conf.getString("db.url")
  val user = conf.getString("db.user")
  val password = conf.getString("db.password")

  val logo = new Image(Image.getClass.getResourceAsStream("/logo.white.png"))

  def loadImageView(path: String): ImageView = new ImageView {
    image = new Image(Image.getClass.getResourceAsStream(path))
    fitHeight = 25
    fitWidth = 25
    preserveRatio = true
    smooth = true
  }