package pool

import scalafx.Includes.*
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.{BorderPane, HBox, Priority, VBox}

import pool.pane.{PoolsPane, TabbedPane}
import pool.pane.dashboard.DashboardPane

final class View(context: Context):
  val borderPane = new BorderPane { 
    prefWidth = context.windowWidth
    prefHeight = context.windowHeight
    padding = Insets(6)
  }

  val dashboardPane = DashboardPane(context)
  HBox.setHgrow(dashboardPane, Priority.Always)
  borderPane.top = dashboardPane

  val poolsPane = PoolsPane(context)
  VBox.setVgrow(poolsPane, Priority.Always)
  borderPane.left = poolsPane

  val tabbedPane = TabbedPane(context)
  VBox.setVgrow(tabbedPane, Priority.Always)
  borderPane.center = tabbedPane

  val scene = new Scene {
    root = borderPane
    stylesheets = List("/style.css")
  }