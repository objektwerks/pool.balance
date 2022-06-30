package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, XYChart}
import scalafx.scene.control.{Tab, TabPane}

import pool.Context

class MeasurementsChart(context: Context) extends TabPane:
  val measurements = context.model.observableMeasurements.reverse
  val formatter = DateTimeFormatter.ofPattern("M.dd")
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

  val calciumHardnessTab = new Tab {
    closable = false
    text = context.chartCalciumHardness
    content = buildCalciumHardnessChart()
  }

  val totalAlkalinityTab = new Tab {
    closable = false
    text = context.chartTotalAlkalinity
    content = buildTotalAlkalinityChart()
  }

  val cyanuricAcidTab = new Tab {
    closable = false
    text = context.chartCyanuricAcid
    content = buildCyanuricAcidChart()
  }

  val totalBromineTab = new Tab {
    closable = false
    text = context.chartTotalBromine
    content = buildTotalBromineChart()
  }

  val saltTab = new Tab {
    closable = false
    text = context.chartSalt
    content = buildSaltChart()
  }

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

  def buildTotalChlorineChart(): LineChart[Number, Number] =
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
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.totalChlorine)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildFreeChlorineChart(): LineChart[Number, Number] =
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
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.freeChlorine)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildCombinedChlorineChart(): LineChart[Number, Number] =
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
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
    LineChartBuilder.addTooltip(chart)
    chart

  def buildPhChart(): LineChart[Number, Number] =
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
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.ph)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildCalciumHardnessChart(): LineChart[Number, Number] =
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
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.calciumHardness)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildTotalAlkalinityChart(): LineChart[Number, Number] =
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
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.totalAlkalinity)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildCyanuricAcidChart(): LineChart[Number, Number] =
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
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.cyanuricAcid)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildTotalBromineChart(): LineChart[Number, Number] =
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
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.totalBromine)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildSaltChart(): LineChart[Number, Number] =
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
      series.data() += XYChart.Data[Number, Number](m.measured.format(formatter).toDouble, m.salt)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart