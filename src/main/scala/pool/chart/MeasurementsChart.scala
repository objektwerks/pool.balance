package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, XYChart}
import scalafx.collections.ObservableBuffer
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
                                         yUpperBound = 10,
                                         yLowerBound = 0,
                                         yTickUnit = 1)
    measurements foreach { measurement =>
      series.data() += XYChart.Data[Number, Number](measurement.measured.format(dateFormatter).toDouble, measurement.totalChlorine) 
    }
    chart.data = series
    val min = measurements.map(m => m.totalChlorine).min
    val max = measurements.map(m => m.totalChlorine).max
    val avg = measurements.map(m => m.totalChlorine).sum / measurements.length
    series.name = s"${context.chartMin} $min  ${context.chartMax} $max  ${context.chartAvg} $avg"
    chart