package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, XYChart}
import scalafx.scene.control.{Tab, TabPane}

import pool.Context

class MeasurementsChart(context: Context) extends TabPane:
  val measurements = context.model.observableMeasurements
  val formatter = DateTimeFormatter.ofPattern("yy.M")
  val minDate = measurements.map(m => m.measured).min.format(formatter).toDouble
  val maxDate = measurements.map(m => m.measured).max.format(formatter).toDouble

  val totalChlorineTab = new Tab {
    closable = false
    text = context.chartTotalChlorine
    content = buildTotalChlorineChart()
  }

  padding = Insets(6)
  tabs = List(totalChlorineTab)

  def buildTotalChlorineChart(): LineChart[Number, Number] =
    val (chart, series, min, max, avg) = LineChartBuilder.build(xLabel = context.chartYearMonth,
                                                                minDate,
                                                                maxDate,
                                                                yLabel = context.chartTotalChlorine,
                                                                yLowerBound = 0,
                                                                yUpperBound = 10,
                                                                yTickUnit = 1,
                                                                measurements.map(m => m.totalChlorine))
    measurements foreach { m =>
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.totalChlorine)
    }
    chart.data = series
    series.name = s"${context.chartMin} $min  ${context.chartMax} $max  ${context.chartAvg} $avg"
    chart