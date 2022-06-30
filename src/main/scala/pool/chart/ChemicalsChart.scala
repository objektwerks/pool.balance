package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, XYChart}
import scalafx.scene.chart.XYChart.Series
import scalafx.scene.control.{Tab, TabPane}

import pool.{Chemical, Context}
import pool.TypeOfChemical.*

class ChemicalsChart(context: Context) extends TabPane:
  val chemicals = context.model.observableChemicals.reverse
  val formatter = DateTimeFormatter.ofPattern("M.dd")
  val minDate = chemicals.map(c => c.added).min.format(formatter).toDouble
  val maxDate = chemicals.map(c => c.added).max.format(formatter).toDouble

  val liquidChlorineTab = new Tab {
    closable = false
    text = context.chartTotalChlorine
    content = buildLiquidChlorineChart()
  }

  val trichlorTab = new Tab {
    closable = false
    text = context.chartTrichlor
    content = buildTrichlorChart()
  }

  padding = Insets(6)
  tabs = List(liquidChlorineTab,
               trichlorTab)

  private def buildChart(filtered: ObservableBuffer[Chemical],
                         series: Series[Number, Number],
                         chart: LineChart[Number, Number]): LineChart[Number, Number] =
    filtered foreach { c =>
      series.data() += XYChart.Data[Number, Number](c.added.format(formatter).toDouble, c.amount)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildLiquidChlorineChart(): LineChart[Number, Number] =
    val filtered = chemicals filter(c => c.typeof == LiquidChlorine)
    val (chart, series, min, max, avg) = LineChartBuilder.build(context = context,
                                                                xLabel = context.chartMonthDay,
                                                                xMinDate = minDate,
                                                                xMaxDate = maxDate,
                                                                yLabel = context.chartLiquidChlorine,
                                                                yLowerBound = 1,
                                                                yUpperBound = 40,
                                                                yTickUnit = 5,
                                                                yValues = filtered.map(c => c.amount))
    buildChart(filtered, series, chart)

  def buildTrichlorChart(): LineChart[Number, Number] =
    val filtered = chemicals filter(c => c.typeof == Trichlor)
    val (chart, series, min, max, avg) = LineChartBuilder.build(context = context,
                                                                xLabel = context.chartMonthDay,
                                                                xMinDate = minDate,
                                                                xMaxDate = maxDate,
                                                                yLabel = context.chartTrichlor,
                                                                yLowerBound = 1,
                                                                yUpperBound = 10,
                                                                yTickUnit = 1,
                                                                yValues = filtered.map(c => c.amount))
    buildChart(filtered, series, chart)