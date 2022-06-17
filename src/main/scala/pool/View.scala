package pool

import scalafx.Includes.*
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control.{Separator, SplitPane}
import scalafx.scene.layout.{HBox, Priority, VBox}

import pool.pane.{PoolsPane, TabbedPane}
import pool.pane.dashboard.DashboardPane

final class View(context: Context):
  val poolPane = PoolsPane(context)
  val dashboardPane = DashboardPane(context)
  val northPane = new HBox {
    children = List(poolPane, Separator(Orientation.Vertical), dashboardPane)
  }
  HBox.setHgrow(poolPane, Priority.Always)
  HBox.setHgrow(dashboardPane, Priority.Always)

  val tabbedPane = TabbedPane(context)
  val southPane = new VBox {
    children = List(tabbedPane)
  }
  VBox.setVgrow(tabbedPane, Priority.Always)

  val splitPane = new SplitPane {
    orientation = Orientation.Vertical
    items.addAll(northPane, southPane)
  }
  splitPane.setDividerPositions(0.3, 0.7)
  VBox.setVgrow(northPane, Priority.Always)
  VBox.setVgrow(southPane, Priority.Always)

  val rootPane = new VBox {
    prefWidth = context.windowWidth
    prefHeight = context.windowHeight
    spacing = 6
    padding = Insets(6)
    children = List(splitPane)
  }
  VBox.setVgrow(splitPane, Priority.Always)

  val scene = new Scene {
    root = rootPane
    stylesheets = List("/style.css")
  }