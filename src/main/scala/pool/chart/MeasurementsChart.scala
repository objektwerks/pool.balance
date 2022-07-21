package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, XYChart}
import scalafx.scene.control.{Tab, TabPane}

import pool.Context

final class MeasurementsChart(context: Context) extends TabPane:
  val measurements = context.model.observableMeasurements.reverse
  val dateFormat = DateTimeFormatter.ofPattern("M.dd")
  val minDate = measurements.map(m => m.measured).min.format(dateFormat)
  val maxDate = measurements.map(m => m.measured).max.format(dateFormat)

  val totalChlorineTab = new Tab:
    closable = false
    text = context.chartTotalChlorine
    content = buildTotalChlorineChart()

  val freeChlorineTab = new Tab:
    closable = false
    text = context.chartFreeChlorine
    content = buildFreeChlorineChart()

  val combinedChlorineTab = new Tab:
    closable = false
    text = context.chartCombinedChlorine
    content = buildCombinedChlorineChart()

  val phTab = new Tab:
    closable = false
    text = context.chartPh
    content = buildPhChart()

  val calciumHardnessTab = new Tab:
    closable = false
    text = context.chartCalciumHardness
    content = buildCalciumHardnessChart()

  val totalAlkalinityTab = new Tab:
    closable = false
    text = context.chartTotalAlkalinity
    content = buildTotalAlkalinityChart()

  val cyanuricAcidTab = new Tab:
    closable = false
    text = context.chartCyanuricAcid
    content = buildCyanuricAcidChart()

  val totalBromineTab = new Tab:
    closable = false
    text = context.chartTotalBromine
    content = buildTotalBromineChart()

  val saltTab = new Tab:
    closable = false
    text = context.chartSalt
    content = buildSaltChart()

  padding = Insets(6)
  tabs = List(totalChlorineTab,
              freeChlorineTab,
              combinedChlorineTab,
              phTab,
              calciumHardnessTab,
              totalAlkalinityTab,
              cyanuricAcidTab,
              totalBromineTab,
              saltTab)

  def buildTotalChlorineChart(): LineChart[String, Number] =
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
                                                 xMinDate = minDate,
                                                 xMaxDate = maxDate,
                                                 yLabel = context.chartTotalChlorine,
                                                 yLowerBound = 0,
                                                 yUpperBound = 10,
                                                 yTickUnit = 1,
                                                 yValues = measurements.map(m => m.totalChlorine))
    measurements foreach { m =>
      series.data() += XYChart.Data[String, Number](m.measured.format(dateFormat), m.totalChlorine)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildFreeChlorineChart(): LineChart[String, Number] =
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
                                                 xMinDate = minDate,
                                                 xMaxDate = maxDate,
                                                 yLabel = context.chartTotalChlorine,
                                                 yLowerBound = 0,
                                                 yUpperBound = 10,
                                                 yTickUnit = 1,
                                                 yValues = measurements.map(m => m.freeChlorine))
    measurements foreach { m =>
      series.data() += XYChart.Data[String, Number](m.measured.format(dateFormat), m.freeChlorine)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildCombinedChlorineChart(): LineChart[String, Number] =
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
                                                 xMinDate = minDate,
                                                 xMaxDate = maxDate,
                                                 yLabel = context.chartCombinedChlorine,
                                                 yLowerBound = -0.1,
                                                 yUpperBound = 0.7,
                                                 yTickUnit = 0.1,
                                                 yValues = measurements.map(m => m.combinedChlorine))
    measurements foreach { m =>
      series.data() += XYChart.Data[String, Number](m.measured.format(dateFormat), m.combinedChlorine)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildPhChart(): LineChart[String, Number] =
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
                                                 xMinDate = minDate,
                                                 xMaxDate = maxDate,
                                                 yLabel = context.chartPh,
                                                 yLowerBound = 6.2,
                                                 yUpperBound = 8.4,
                                                 yTickUnit = 0.1,
                                                 yValues = measurements.map(m => m.ph))
    measurements foreach { m =>
      series.data() += XYChart.Data[String, Number](m.measured.format(dateFormat), m.ph)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildCalciumHardnessChart(): LineChart[String, Number] =
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
                                                 xMinDate = minDate,
                                                 xMaxDate = maxDate,
                                                 yLabel = context.chartCalciumHardness,
                                                 yLowerBound = 0,
                                                 yUpperBound = 1000,
                                                 yTickUnit = 100,
                                                 yValues = measurements.map(m => m.calciumHardness))
    measurements foreach { m =>
      series.data() += XYChart.Data[String, Number](m.measured.format(dateFormat), m.calciumHardness)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildTotalAlkalinityChart(): LineChart[String, Number] =
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
                                                 xMinDate = minDate,
                                                 xMaxDate = maxDate,
                                                 yLabel = context.chartTotalAlkalinity,
                                                 yLowerBound = 0,
                                                 yUpperBound = 240,
                                                 yTickUnit = 20,
                                                 yValues = measurements.map(m => m.totalAlkalinity))
    measurements foreach { m =>
      series.data() += XYChart.Data[String, Number](m.measured.format(dateFormat), m.totalAlkalinity)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildCyanuricAcidChart(): LineChart[String, Number] =
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
                                                 xMinDate = minDate,
                                                 xMaxDate = maxDate,
                                                 yLabel = context.chartCyanuricAcid,
                                                 yLowerBound = 0,
                                                 yUpperBound = 300,
                                                 yTickUnit = 30,
                                                 yValues = measurements.map(m => m.cyanuricAcid))
    measurements foreach { m =>
      series.data() += XYChart.Data[String, Number](m.measured.format(dateFormat), m.cyanuricAcid)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildTotalBromineChart(): LineChart[String, Number] =
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
                                                 xMinDate = minDate,
                                                 xMaxDate = maxDate,
                                                 yLabel = context.chartTotalBromine,
                                                 yLowerBound = 0,
                                                 yUpperBound = 20,
                                                 yTickUnit = 1,
                                                 yValues = measurements.map(m => m.totalBromine))
    measurements foreach { m =>
      series.data() += XYChart.Data[String, Number](m.measured.format(dateFormat), m.totalBromine)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildSaltChart(): LineChart[String, Number] =
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
                                                 xMinDate = minDate,
                                                 xMaxDate = maxDate,
                                                 yLabel = context.chartSalt,
                                                 yLowerBound = 0,
                                                 yUpperBound = 3600,
                                                 yTickUnit = 300,
                                                 yValues = measurements.map(m => m.salt))
    measurements foreach { m =>
      series.data() += XYChart.Data[String, Number](m.measured.format(dateFormat), m.salt)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart