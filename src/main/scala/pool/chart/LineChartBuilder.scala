package pool.chart

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.Node
import scalafx.scene.chart.{CategoryAxis, LineChart, NumberAxis, XYChart}
import scalafx.scene.control.Tooltip

import pool.Context
import scalafx.scene.chart.CategoryAxis
import scala.util.Try

object LineChartBuilder:
  def build(context: Context,
            xLabel: String,
            xMinDate: String,
            xMaxDate: String,
            yLabel: String,
            yLowerBound: Double,
            yUpperBound: Double,
            yTickUnit: Double,
            yValues: ObservableBuffer[Double]): (LineChart[String, Number],
                                                 XYChart.Series[String, Number]) =
    val xAxis = CategoryAxis(axisLabel = s"$xLabel [$xMinDate - $xMaxDate]")
    val yAxis = NumberAxis(axisLabel = yLabel,
                           lowerBound = yLowerBound,
                           upperBound = yUpperBound,
                           tickUnit = yTickUnit)
    val chart = LineChart[String, Number](xAxis, yAxis)
    val series = new XYChart.Series[String, Number]()
    chart.padding = Insets(6)
    val (min, max, avg) = toMinMaxAvg(yValues)
    series.name = s"${context.chartMin} $min  ${context.chartMax} $max  ${context.chartAvg} $avg"
    (chart, series)

  def addTooltip(chart: LineChart[String, Number]): Unit =
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
    val min = numbers.minOption.getOrElse(0.0)
    val max = numbers.maxOption.getOrElse(0.0)
    val avg = Try { ( numbers.sum / numbers.length ).toDouble }.toOption.getOrElse(0.0)
    (min, max, avg)