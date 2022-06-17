package pool.pane.dashboard

import scalafx.geometry.Insets
import scalafx.scene.layout.{HBox, Priority}

import pool.Context

class DashboardPane(context: Context) extends HBox:
  spacing = 6
  padding = Insets(6)

  val freeClorinePane = FreeChlorinePane(context)
  HBox.setHgrow(freeClorinePane, Priority.Always)

  val phPane = PhPane(context)
  HBox.setHgrow(freeClorinePane, Priority.Always)

  val totalAlkalinityPane = TotalAlkalinityPane(context)
  HBox.setHgrow(freeClorinePane, Priority.Always)

  val calciumHardnessPane = CalciumHardnessPane(context)
  HBox.setHgrow(freeClorinePane, Priority.Always)

  val cyanuricAcidPane = CyanuricAcidPane(context)
  HBox.setHgrow(freeClorinePane, Priority.Always)

  val totalBrominePane = TotalBrominePane(context)
  HBox.setHgrow(freeClorinePane, Priority.Always)


  children = List(freeClorinePane,
                  phPane, 
                  totalAlkalinityPane, 
                  calciumHardnessPane, 
                  cyanuricAcidPane, 
                  totalBrominePane)