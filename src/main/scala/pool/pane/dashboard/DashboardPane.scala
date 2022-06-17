package pool.pane.dashboard

import scalafx.geometry.Insets
import scalafx.scene.layout.HBox

import pool.Context

class DashboardPane(context: Context) extends HBox:
  spacing = 6
  padding = Insets(6)

  val freeClorinePane = FreeChlorinePane(context)
  val phPane = PhPane(context)
  val totalAlkalinityPane = TotalAlkalinityPane(context)
  val calciumHardnessPane = CalciumHardnessPane(context)
  val cyanuricAcidPane = CyanuricAcidPane(context)
  val totalBrominePane = TotalBrominePane(context)

  children = List(freeClorinePane,
                  phPane, 
                  totalAlkalinityPane, 
                  calciumHardnessPane, 
                  cyanuricAcidPane, 
                  totalBrominePane)