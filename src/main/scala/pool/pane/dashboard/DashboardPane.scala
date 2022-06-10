package pool.pane.dashboard

import scalafx.geometry.Insets
import scalafx.scene.layout.HBox

import pool.Context

class DashboardPane(context: Context) extends HBox:
  spacing = 6
  padding = Insets(6)

  children = List(FreeChlorinePane(context),
                  PhPane(context),
                  TotalAlkalinityPane(context),
                  CalciumHardnessPane(context),
                  CyanuricAcidPane(context))