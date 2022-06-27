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

  val freeChlorineTab = new Tab {
    closable = false
    text = context.chartFreeChlorine
    content = buildFreeChlorineChart()
  }

  val combinedChlorineTab = new Tab {
    closable = false
    text = context.chartCombinedChlorine
    content = buildCombinedChlorineChart()
  }

  val phTab = new Tab {
    closable = false
    text = context.chartPh
    content = buildPhChart()
  }

  padding = Insets(6)
  tabs = List(totalChlorineTab,
              freeChlorineTab,
              combinedChlorineTab,
              phTab)

  def buildTotalChlorineChart(): LineChart[Number, Number] =
    val (chart, series, min, max, avg) = LineChartBuilder.build(context = context,
                                                                xLabel = context.chartYearMonth,
                                                                xMinDate = minDate,
                                                                xMaxDate = maxDate,
                                                                yLabel = context.chartTotalChlorine,
                                                                yLowerBound = 0,
                                                                yUpperBound = 10,
                                                                yTickUnit = 1,
                                                                yValues = measurements.map(m => m.totalChlorine))
    measurements foreach { m =>
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.totalChlorine)
    }
    chart.data = series
    chart

  def buildFreeChlorineChart(): LineChart[Number, Number] =
    val (chart, series, min, max, avg) = LineChartBuilder.build(context = context,
                                                                xLabel = context.chartYearMonth,
                                                                xMinDate = minDate,
                                                                xMaxDate = maxDate,
                                                                yLabel = context.chartTotalChlorine,
                                                                yLowerBound = 0,
                                                                yUpperBound = 10,
                                                                yTickUnit = 1,
                                                                yValues = measurements.map(m => m.freeChlorine))
    measurements foreach { m =>
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.freeChlorine)
    }
    chart.data = series
    chart

  def buildCombinedChlorineChart(): LineChart[Number, Number] =
    val (chart, series, min, max, avg) = LineChartBuilder.build(context = context,
                                                                xLabel = context.chartYearMonth,
                                                                xMinDate = minDate,
                                                                xMaxDate = maxDate,
                                                                yLabel = context.chartCombinedChlorine,
                                                                yLowerBound = 0.0,
                                                                yUpperBound = 0.5,
                                                                yTickUnit = 0.1,
                                                                yValues = measurements.map(m => m.combinedChlorine))
    measurements foreach { m =>
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.combinedChlorine)
    }
    chart.data = series
    chart

  def buildPhChart(): LineChart[Number, Number] =
    val (chart, series, min, max, avg) = LineChartBuilder.build(context = context,
                                                                xLabel = context.chartYearMonth,
                                                                xMinDate = minDate,
                                                                xMaxDate = maxDate,
                                                                yLabel = context.chartPh,
                                                                yLowerBound = 0.0,
                                                                yUpperBound = 0.5,
                                                                yTickUnit = 0.1,
                                                                yValues = measurements.map(m => m.ph))
    measurements foreach { m =>
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.ph)
    }
    chart.data = series
    chart