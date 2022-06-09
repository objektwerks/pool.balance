package pool

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, VBox}

final class View(context: Context):
  val eastPane = new VBox {
    children = List(context.dashboardPane, context.contentPane)
  }

  val westPane = new VBox {
    children = List(context.poolPane, context.menuPane)
  }

  val rootPane = new HBox {
    prefWidth = context.windowWidth
    prefHeight = context.windowHeight
    spacing = 6
    padding = Insets(6)
    children = List(westPane, eastPane)
  }

  val scene = new Scene {
    root = rootPane
  }