package pool.pane

import scalafx.geometry.Insets
import scalafx.scene.control.TitledPane
import scalafx.scene.control.Label

import pool.Context

class FreeChlorinePane(context: Context) extends TitledPane:
  collapsible = false
  padding = Insets(6)
  text = context.tableFreeChlorine

  val current = new Label {
    text = context.labelFreeChlorine
  }

  val average = new Label {
    text = context.labelFreeChlorine
  }