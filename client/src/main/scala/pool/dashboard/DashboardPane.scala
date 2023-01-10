package pool.dashboard

import scalafx.geometry.Insets
import scalafx.scene.layout.{HBox, Priority}

import pool.Context

final class DashboardPane(context: Context) extends HBox:
  spacing = 6
  padding = Insets(6)

  val totalChlorinePane = TotalChlorinePane(context)
  HBox.setHgrow(totalChlorinePane, Priority.Always)

  val freeClorinePane = FreeChlorinePane(context)
  HBox.setHgrow(freeClorinePane, Priority.Always)

  val combinedChlorinePane = CombinedChlorinePane(context)
  HBox.setHgrow(combinedChlorinePane, Priority.Always)

  val phPane = PhPane(context)
  HBox.setHgrow(phPane, Priority.Always)

  val calciumHardnessPane = CalciumHardnessPane(context)
  HBox.setHgrow(calciumHardnessPane, Priority.Always)

  val totalAlkalinityPane = TotalAlkalinityPane(context)
  HBox.setHgrow(totalAlkalinityPane, Priority.Always)

  val cyanuricAcidPane = CyanuricAcidPane(context)
  HBox.setHgrow(cyanuricAcidPane, Priority.Always)

  val totalBrominePane = TotalBrominePane(context)
  HBox.setHgrow(totalBrominePane, Priority.Always)

  val saltPane = SaltPane(context)
  HBox.setHgrow(saltPane, Priority.Always)

  children = List(totalChlorinePane,
                  freeClorinePane,
                  combinedChlorinePane,
                  phPane, 
                  calciumHardnessPane, 
                  totalAlkalinityPane, 
                  cyanuricAcidPane, 
                  totalBrominePane,
                  saltPane)