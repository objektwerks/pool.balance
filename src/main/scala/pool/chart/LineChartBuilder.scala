package pool.chart

import java.text.DecimalFormat

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.Node
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import scalafx.scene.control.Tooltip

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
                                                 XYChart.Series[Number, Number]) =
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
    val formatter = new DecimalFormat("#.##");
    series.name = s"${context.chartMin} $min  ${context.chartMax} $max  ${context.chartAvg} ${formatter.format(avg)}"
    (chart, series)

  def addTooltip(chart: LineChart[Number, Number]): Unit =
    chart.data().foreach { items =>
      items.data.value.forEach { item =>
        val xValue = item.XValue.value
        val yValue = item.YValue.value
        val message = s"$yValue on $xValue"
        val tooltip = new Tooltip(message)
        val node: Node = item.node.value
        Tooltip.install(node, tooltip)
      }
    }

  private def toMinMaxAvg(numbers: ObservableBuffer[Double]): (Double, Double, Double) =
    val min = numbers.min
    val max = numbers.max
    val avg = numbers.sum / numbers.length
    (min, max, avg)