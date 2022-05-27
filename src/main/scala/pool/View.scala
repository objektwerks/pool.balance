package pool

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

final class View(conf: Conf, model: Model):
  val scene = new Scene {
    root = new VBox {
      prefWidth = conf.windowWidth
      prefHeight = conf.windowHeight
      spacing = 6
      padding = Insets(6)
      children = List()
    }
  }