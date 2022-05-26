package objektwerks

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

import Conf.*

object View:
  val scene = new Scene {
    root = new VBox {
      prefWidth = windowWidth
      prefHeight = windowHeight
      spacing = 6
      padding = Insets(6)
      children = List()
    }
  }