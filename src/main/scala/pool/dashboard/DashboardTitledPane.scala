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

  def inRangeCurrent: Unit =
    current.style = ""

  def outOfRangeCurrent: Unit =
    logger.info(s"DashboardTitledPane.outOfRangeCurrent: ${text.value}")
    current.style = "-fx-border-color: red; -fx-border-width: 3;"

  def inRangeAverage: Unit =
    average.style = ""

  def outOfRangeAverage: Unit =
    logger.info(s"DashboardTitledPane.outOfRangeAverage: ${text.value}")
    average.style = "-fx-border-color: red; -fx-border-width: 3;"