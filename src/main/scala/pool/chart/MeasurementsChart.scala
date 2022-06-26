package pool.chart

import java.time.format.DateTimeFormatter

import scalafx.geometry.Insets
import scalafx.scene.control.TabPane

import pool.Context

class MeasurementsChart(context: Context) extends TabPane with Chart:
  val measurements = context.model.observableMeasurements
  val dateFormatter = DateTimeFormatter.ofPattern("yy.D")
  val minDate = measurements.map(m => m.measured).min.format(dateFormatter).toDouble
  val maxDate = measurements.map(m => m.measured).max.format(dateFormatter).toDouble

  padding = Insets(6)
  tabs = List()