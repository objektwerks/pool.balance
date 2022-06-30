package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.scene.control.TabPane

import pool.Context

class CleaningsChart(context: Context) extends TabPane:
  val cleanings = context.model.observableCleanings.reverse
  val formatter = DateTimeFormatter.ofPattern("M.dd")
  val minDate = cleanings.map(c => c.cleaned).min.format(formatter).toDouble
  val maxDate = cleanings.map(c => c.cleaned).max.format(formatter).toDouble