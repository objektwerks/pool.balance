package pool.chart

import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}

import pool.Context

object LineChartBuilder:
  def build(context: Context,
            xLabel: String,
            xMinDate: Double,
            xMaxDate: Double,
            yLabel: String,
            yLowerBound: Double,
            yUpperBound: Double,
            yTickUnit: Double,
            yValues: ObservableBuffer[Double]): (LineChart[Number, Number],
                                                 XYChart.Series[Number, Number],
                                                 Number,
                                                 Number,
                                                 Number) =
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
    val (min, max, avg) = toMinMaxAvg(yValues)
    series.name = s"${context.chartMin} $min  ${context.chartMax} $max  ${context.chartAvg} $avg"
    (chart, series, min, max, avg)

  private def toMinMaxAvg(numbers: ObservableBuffer[Double]): (Double, Double, Double) =
    val min = numbers.min
    val max = numbers.max
    val avg = numbers.sum / numbers.length
    (min, max, avg)