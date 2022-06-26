package pool.chart

import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}

import pool.Context

trait Chart:
  def buildLineChart(xLabel: String,
                     xMinDate: Double,
                     xMaxDate: Double,
                     yLabel: String,
                     yLowerBound: Double,
                     yUpperBound: Double,
                     yTickUnit: Double): (LineChart[Number, Number], XYChart.Series[Number, Number]) =
    val xAxis = NumberAxis(axisLabel = s"$xLabel [$xMinDate - $xMaxDate]",
                           lowerBound = xMinDate,
                           upperBound = xMaxDate,
                           tickUnit = 1)
    val yAxis = NumberAxis(axisLabel = yLabel,
                           lowerBound = yLowerBound,
                           upperBound = yUpperBound,
                           tickUnit = yTickUnit)
    val chart = LineChart[Number, Number](xAxis, yAxis)
    val series = new XYChart.Series[Number, Number]()
    chart.padding = Insets(6)
    (chart, series)