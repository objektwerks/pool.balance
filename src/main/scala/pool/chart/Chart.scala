package pool.chart

import scalafx.collections.ObservableBuffer
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

  def minMaxAvgAsInt(integers: ObservableBuffer[Int]): (Int, Int, Int) =
    val min = integers.min
    val max = integers.max
    val avg = integers.sum / integers.length
    (min, max, avg)

  def minMaxAvgAsDouble(doubles: ObservableBuffer[Double]): (Double, Double, Double) =
    val min = doubles.min
    val max = doubles.max
    val avg = doubles.sum / doubles.length
    (min, max, avg)