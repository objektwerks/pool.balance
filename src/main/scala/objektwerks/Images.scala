package objektwerks

import scalafx.scene.image.{Image, ImageView}

object Images:
  val logo = new Image(Images.getClass.getResourceAsStream("/logo.white.png"))

  def loadImageView(path: String): ImageView = new ImageView {
    image = new Image(Images.getClass.getResourceAsStream(path))
    fitHeight = 25
    fitWidth = 25
    preserveRatio = true
    smooth = true
  }