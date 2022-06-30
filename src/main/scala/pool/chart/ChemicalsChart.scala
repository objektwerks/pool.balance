package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.chart.{LineChart, XYChart}
import scalafx.scene.control.{Tab, TabPane}

import pool.{Chemical, Context}
import pool.TypeOfChemical.*
import pool.TypeOfChemical

class ChemicalsChart(context: Context) extends TabPane:
  val chemicals = context.model.observableChemicals.reverse
  val dateFormat = DateTimeFormatter.ofPattern("M.dd")
  val minDate = chemicals.map(c => c.added).min.format(dateFormat).toDouble
  val maxDate = chemicals.map(c => c.added).max.format(dateFormat).toDouble

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

  val dichlorTab = new Tab {
    closable = false
    text = context.chartDichlor
    content = buildDichlorChart()
  }

  val calciumHypochloriteTab = new Tab {
    closable = false
    text = context.chartCalciumHypochlorite
    content = buildCalciumHypochloriteChart()
  }

  val stabilizerTab = new Tab {
    closable = false
    text = context.chartStabilizer
    content = buildStabilizerChart()
  }

  val algaecideTab = new Tab {
    closable = false
    text = context.chartAlgaecide
    content = buildAlgaecideChart()
  }

  val muriaticAcidTab = new Tab {
    closable = false
    text = context.chartMuriaticAcid
    content = buildMuriaticAcidChart()
  }

  val saltTab = new Tab {
    closable = false
    text = context.chartSalt
    content = buildSaltChart()
  }

  padding = Insets(6)
  tabs = List(liquidChlorineTab,
               trichlorTab,
               dichlorTab,
               calciumHypochloriteTab,
               stabilizerTab,
               algaecideTab,
               muriaticAcidTab,
               saltTab)

  private def buildChart(typeof: TypeOfChemical,
                         yLabel: String,
                         yLowerBound: Int = 1,
                         yUpperBound: Int = 4,
                         yTickUnit: Int = 1): LineChart[Number, Number] =
    val filtered = chemicals filter(c => c.typeof == typeof)
    val (chart, series) = LineChartBuilder.build(context = context,
                                                 xLabel = context.chartMonthDay,
                                                 xMinDate = minDate,
                                                 xMaxDate = maxDate,
                                                 yLabel = yLabel,
                                                 yLowerBound = yLowerBound,
                                                 yUpperBound = yUpperBound,
                                                 yTickUnit = yTickUnit,
                                                 yValues = filtered.map(c => c.amount))
    filtered foreach { c =>
      series.data() += XYChart.Data[Number, Number](c.added.format(dateFormat).toDouble, c.amount)
    }
    chart.data = series
    LineChartBuilder.addTooltip(chart)
    chart

  def buildLiquidChlorineChart(): LineChart[Number, Number] =
    buildChart(typeof = LiquidChlorine, yLabel = context.chartLiquidChlorine)

  def buildTrichlorChart(): LineChart[Number, Number] =
    buildChart(typeof = Trichlor, yLabel = context.chartTrichlor)

  def buildDichlorChart(): LineChart[Number, Number] =
    buildChart(typeof = Dichlor, yLabel = context.chartDichlor)

  def buildCalciumHypochloriteChart(): LineChart[Number, Number] =
    buildChart(typeof = CalciumHypochlorite, yLabel = context.chartCalciumHypochlorite)

  def buildStabilizerChart(): LineChart[Number, Number] =
    buildChart(typeof = Stabilizer, yLabel = context.chartStabilizer)

  def buildAlgaecideChart(): LineChart[Number, Number] =
    buildChart(typeof = Algaecide, yLabel = context.chartAlgaecide)

  def buildMuriaticAcidChart(): LineChart[Number, Number] =
    buildChart(typeof = MuriaticAcid, yLabel = context.chartMuriaticAcid)

  def buildSaltChart(): LineChart[Number, Number] =
    buildChart(typeof = Salt, yLabel = context.chartSalt)