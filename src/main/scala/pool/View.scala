package pool

import scalafx.Includes._
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control.SplitPane
import scalafx.scene.layout.{HBox, VBox}

import pool.pane.{DashboardPane, PoolPane, TabbedPane}

final class View(context: Context):
  val poolPane = PoolPane(context)
  val dashboardPane = DashboardPane(context)
  val westPane = new VBox {
    children = List(poolPane, dashboardPane)
  }

  val tabbedPane = TabbedPane(context)
  val eastPane = new VBox {
    children = List(tabbedPane)
  }

  val splitPane = new SplitPane {
    orientation = Orientation.Horizontal
    padding = Insets(6)
    prefWidth = context.windowWidth
    prefHeight = context.windowHeight
    items.addAll(westPane, eastPane)
  }
  splitPane.setDividerPositions(0.3, 0.7)

  val rootPane = new VBox {
    spacing = 6
    padding = Insets(6)
    children = List(splitPane)
  }

  val scene = new Scene {
    root = rootPane
  }