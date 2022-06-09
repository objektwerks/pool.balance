package pool.pane

import pool.Context

import scalafx.scene.layout.VBox
import scalafx.scene.control.Label

class PoolPane(context: Context) extends VBox:
  val poolLabel = new Label {
    text = context.labelPools
  }