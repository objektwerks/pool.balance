package pool

import scalafx.Includes.*
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control.{Separator, SplitPane}
import scalafx.scene.layout.{HBox, VBox}

import pool.pane.{PoolsPane, TabbedPane}
import pool.pane.dashboard.DashboardPane

final class View(context: Context):
  val poolPane = PoolsPane(context)
  poolPane.prefWidth = context.windowWidth * 0.425
  val dashboardPane = DashboardPane(context)
  val northPane = new HBox {
    children = List(poolPane, Separator(Orientation.Vertical), dashboardPane)
  }

  val tabbedPane = TabbedPane(context)
  val southPane = new VBox {
    children = List(tabbedPane)
  }

  val splitPane = new SplitPane {
    orientation = Orientation.Vertical
    prefWidth = context.windowWidth
    prefHeight = context.windowHeight
    items.addAll(northPane, southPane)
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