package pool

import scalafx.Includes._
import scalafx.scene.control.SplitPane
import scalafx.geometry.{Insets, Orientation}
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

  val splitPane = new SplitPane {
    orientation = Orientation.Vertical
    padding = Insets(6)
    items.addAll(northPane, southPane)
  }
  southPane.prefHeightProperty() <== splitPane.heightProperty()
  splitPane.setDividerPositions(0.3, 0.7)

  val rootPane = new VBox {
    prefWidth = context.windowWidth
    prefHeight = context.windowHeight
    spacing = 6
    padding = Insets(6)
    children = List(splitPane)
  }

  val scene = new Scene {
    root = rootPane
  }