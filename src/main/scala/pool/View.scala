package pool

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, VBox}

import pool.pane.{ContentPane, DashboardPane, TabbedPane, PoolPane}

final class View(context: Context):
  val dashboardPane = DashboardPane(context)
  val contentPane = ContentPane(context)
  val eastPane = new VBox {
    children = List(dashboardPane, contentPane)
  }

  val poolPane = PoolPane(context)
  val tabbedPane = TabbedPane(context)
  val westPane = new VBox {
    children = List(poolPane, tabbedPane)
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