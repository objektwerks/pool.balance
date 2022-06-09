package pool

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, VBox}

import pool.pane.{DashboardPane, MenuPane, PoolPane}
import pool.pane.ContentPane

final class View(context: Context):
  val poolPane = PoolPane(context)
  val menuPane = MenuPane(context)

  val westPane = new VBox {
    children = List(poolPane)
  }

  val dashboardPane = DashboardPane(context)
  val contentPane = ContentPane(context)

  val eastPane = new VBox {
    children = List(dashboardPane, contentPane)
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