package pool.dashboard

import com.typesafe.scalalogging.LazyLogging

import scalafx.scene.control.{Label, TitledPane}

import pool.Context
import pool.dialog.ControlGridPane

abstract class DashboardTitledPane(context: Context) extends TitledPane with LazyLogging:
  collapsible = false
  maxWidth = Double.MaxValue
  maxHeight = Double.MaxValue

  val range = new Label:
    text = ""

  val good = new Label:
    text = ""

  val ideal = new Label:
    text = ""
  
  val current = new Label:
    text = "0"
  
  val average = new Label:
    text = "0"

  val controls = List[(String, Label)](
    context.labelRange -> range,
    context.labelGood -> good,
    context.labelIdeal -> ideal,
    context.labelCurrent -> current,
    context.labelAverage -> average
  )
  
  content = ControlGridPane(controls)

  val greenBorderStyle = "-fx-border-color: green; -fx-border-width: 3;"
  val redBorderStyle = "-fx-border-color: red; -fx-border-width: 3;"
  val emptyStyle = ""

  def inRangeCurrent: Unit =
    good.style = emptyStyle
    current.style = emptyStyle

  def outOfRangeCurrent: Unit =
    logger.info(s"DashboardTitledPane.outOfRangeCurrent: ${text.value}")
    good.style = greenBorderStyle
    current.style = redBorderStyle

  def inRangeAverage: Unit =
    good.style = emptyStyle
    average.style = emptyStyle

  def outOfRangeAverage: Unit =
    logger.info(s"DashboardTitledPane.outOfRangeAverage: ${text.value}")
    good.style = greenBorderStyle
    average.style = redBorderStyle