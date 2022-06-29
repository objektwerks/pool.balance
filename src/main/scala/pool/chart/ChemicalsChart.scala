package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, XYChart}
import scalafx.scene.control.{Tab, TabPane}

import pool.Context
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

  padding = Insets(6)
  tabs = List(liquidChlorineTab)

  def buildLiquidChlorineChart(): LineChart[Number, Number] =
    val filtered = chemicals filter(c => c.typeof == LiquidChlorine)
    val (chart, series, min, max, avg) = LineChartBuilder.build(context = context,
                                                                xLabel = context.chartMonthDay,
                                                                xMinDate = minDate,
                                                                xMaxDate = maxDate,
                                                                yLabel = context.chartLiquidChlorine,
                                                                yLowerBound = 0,
                                                                yUpperBound = 10,
                                                                yTickUnit = 1,
                                                                yValues = filtered.map(c => c.amount))
    filtered foreach { c =>
      series.data() += XYChart.Data[Number, Number](c.added.format(formatter).toDouble, c.amount)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart