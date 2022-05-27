package pool

import com.typesafe.config.{Config, ConfigFactory}
import scalafx.scene.image.{Image, ImageView}

final class Conf(config: Config):
  val windowTitle = config.getString("window.title")
  val windowWidth = config.getDouble("window.width")
  val windowHeight = config.getDouble("window.height")

  val url = config.getString("db.url")
  val user = config.getString("db.user")
  val password = config.getString("db.password")

  val logo = new Image(Image.getClass.getResourceAsStream("/logo.white.png"))

object Conf:
  def loadImageView(path: String): ImageView = new ImageView {
    image = new Image(Image.getClass.getResourceAsStream(path))
    fitHeight = 25
    fitWidth = 25
    preserveRatio = true
    smooth = true
  }