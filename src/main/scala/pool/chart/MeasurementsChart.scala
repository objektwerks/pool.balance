package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, XYChart}
import scalafx.scene.control.{Tab, TabPane}

import pool.Context

class MeasurementsChart(context: Context) extends TabPane with Chart:
  val measurements = context.model.observableMeasurements
  val dateFormatter = DateTimeFormatter.ofPattern("yy.D")
  val minDate = measurements.map(m => m.measured).min.format(dateFormatter).toDouble
  val maxDate = measurements.map(m => m.measured).max.format(dateFormatter).toDouble

  val totalChlorineTab = new Tab {
    closable = false
    text = context.chartTotalChlorine
    content = buildTotalChlorineLineChart()
  }

  padding = Insets(6)
  tabs = List(totalChlorineTab)

  def buildTotalChlorineLineChart(): LineChart[Number, Number] =
    val (chart, series) = buildLineChart(xLabel = context.chartYearDay,
                                         minDate,
                                         maxDate,
                                         yLabel = context.chartTotalChlorine,
                                         yLowerBound = 0,
                                         yUpperBound = 10,
                                         yTickUnit = 1)
    measurements foreach { m =>
      series.data() += XYChart.Data[Number, Number](m.measured.format(dateFormatter).toDouble, m.totalChlorine) 
    }
    chart.data = series
    val (min, max, avg) = minMaxAvgAsInt( measurements.map(m => m.totalChlorine) )
    series.name = s"${context.chartMin} $min  ${context.chartMax} $max  ${context.chartAvg} $avg"
    chart