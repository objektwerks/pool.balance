package pool

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, VBox}

import pool.pane.{DashboardPane, PoolPane, TabbedPane}

final class View(context: Context):
  val poolPane = PoolPane(context)
  val dashboardPane = DashboardPane(context)
  val northPane = new HBox {
    children = List(poolPane, dashboardPane)
  }

  val tabbedPane = TabbedPane(context)
  val southPane = new VBox {
    children = List(tabbedPane)
  }

  val rootPane = new VBox {
    prefWidth = context.windowWidth
    prefHeight = context.windowHeight
    spacing = 6
    padding = Insets(6)
    children = List(northPane, southPane)
  }

  val scene = new Scene {
    root = rootPane
  }