package pool

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

import pool.pane.PoolPane

final class View(context: Context):
  val poolPane = PoolPane(context)

  val scene = new Scene {
    root = new VBox {
      prefWidth = context.windowWidth
      prefHeight = context.windowHeight
      spacing = 6
      padding = Insets(6)
      children = List(poolPane)
    }
  }