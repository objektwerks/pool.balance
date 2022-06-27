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

  val totalChlorineSeries = new XYChart.Series[Number, Number]()
  measurements foreach { m =>
    totalChlorineSeries.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.totalChlorine)
  }
  val totalChlorineTab = new Tab {
    closable = false
    text = context.chartTotalChlorine
    content = buildChart(totalChlorineSeries)
  }

  val freeChlorineSeries = new XYChart.Series[Number, Number]()
  measurements foreach { m =>
    freeChlorineSeries.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.freeChlorine)
  }
  val freeChlorineTab = new Tab {
    closable = false
    text = context.chartFreeChlorine
    content = buildChart(freeChlorineSeries)
  }

  padding = Insets(6)
  tabs = List(totalChlorineTab, freeChlorineTab)

  def buildChart(series: XYChart.Series[Number, Number]): LineChart[Number, Number] =
    val (chart, min, max, avg) = LineChartBuilder.build(xLabel = context.chartYearMonth,
                                                        xMinDate = minDate,
                                                        xMaxDate = maxDate,
                                                        yLabel = context.chartTotalChlorine,
                                                        yLowerBound = 0,
                                                        yUpperBound = 10,
                                                        yTickUnit = 1,
                                                        yValues = measurements.map(m => m.totalChlorine))
    series.name = s"${context.chartMin} $min  ${context.chartMax} $max  ${context.chartAvg} $avg"
    chart.data = series
    chart