package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, XYChart}
import scalafx.scene.control.{Tab, TabPane}

import pool.Context
import scalafx.collections.ObservableBuffer

class CleaningsChart(context: Context) extends TabPane:
  val cleanings = context.model.observableCleanings.reverse
  val formatter = DateTimeFormatter.ofPattern("M.dd")
  val minDate = cleanings.map(c => c.cleaned).min.format(formatter).toDouble
  val maxDate = cleanings.map(c => c.cleaned).max.format(formatter).toDouble

  val tab = new Tab {
    closable = false
    text = context.chartCleanings
    content = buildChart()
  }

  padding = Insets(6)
  tabs = List(tab)

  def buildChart(): LineChart[Number, Number] =
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
                                                 xMinDate = minDate,
                                                 xMaxDate = maxDate,
                                                 yLabel = context.chartTotalChlorine,
                                                 yLowerBound = 1,
                                                 yUpperBound = 6,
                                                 yTickUnit = 1,
                                                 yValues = ObservableBuffer(0))
    cleanings foreach { c =>
      series.data() += XYChart.Data[Number, Number](c.cleaned.format(formatter).toDouble, 0)
    }
    chart.data = series
    // LineChartBuilder.addTooltip(chart)
    chart