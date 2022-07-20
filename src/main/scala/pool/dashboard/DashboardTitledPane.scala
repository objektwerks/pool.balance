package pool.dashboard

import scalafx.scene.control.{Label, TitledPane}

import pool.Context
import pool.dialog.ControlGridPane

abstract class DashboardTitledPane(context: Context) extends TitledPane:
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

  def currentInRange: Unit =
    println(s"current in range: $text")
    current.style = "-fx-border-color: darkgreen"

  def currentOutOfRange: Unit = 
    println(s"current out of range: $text")
    current.style = "-fx-border-color: red"

  def averageInRange: Unit =
    println(s"average in range: $text")
    average.style = "-fx-border-color: darkgreen"

  def averageOutOfRange: Unit = 
    println(s"average out of range: $text")
    average.style = "-fx-border-color: red"