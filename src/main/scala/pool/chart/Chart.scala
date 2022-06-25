package pool.chart

import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}

import pool.Context

trait Chart:
  def buildLineChart(context: Context,
                     xMinDate: Double,
                     xMaxDate: Double,
                     yLabel: String,
                     yLowerBound: Double = 0,
                     yUpperBound: Double,
                     yTickUnit: Double): (LineChart[Number, Number], XYChart.Series[Number, Number]) =
    val xAxis = NumberAxis(axisLabel = s"${context.chartYearDay} [$xMinDate - $xMaxDate]",
                           lowerBound = xMinDate,
                           upperBound = xMaxDate,
                           tickUnit = 1)
    val yAxis = NumberAxis(axisLabel = yLabel,
                           lowerBound = yLowerBound,
                           upperBound = yUpperBound,
                           tickUnit = yTickUnit)
    val chart = LineChart[Number, Number](xAxis, yAxis)
    val series = new XYChart.Series[Number, Number]()
    (chart, series)