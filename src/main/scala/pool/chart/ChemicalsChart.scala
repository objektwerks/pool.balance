package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.control.TabPane

import pool.Context

class ChemicalsChart(context: Context) extends TabPane:
  val chemicals = context.model.observableChemicals.reverse
  val formatter = DateTimeFormatter.ofPattern("M.dd")
  val minDate = chemicals.map(c => c.added).min.format(formatter).toDouble
  val maxDate = chemicals.map(c => c.added).max.format(formatter).toDouble

  padding = Insets(6)
  tabs = List()