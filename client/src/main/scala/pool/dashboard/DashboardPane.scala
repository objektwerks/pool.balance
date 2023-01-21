package pool.dashboard

import scalafx.geometry.Insets
import scalafx.scene.layout.{HBox, Priority}

import pool.{Context, Model}

final class DashboardPane(context: Context, model: Model) extends HBox:
  spacing = 6
  padding = Insets(6)

  val totalChlorinePane = TotalChlorinePane(context, model)
  HBox.setHgrow(totalChlorinePane, Priority.Always)

  val freeClorinePane = FreeChlorinePane(context, model)
  HBox.setHgrow(freeClorinePane, Priority.Always)

  val combinedChlorinePane = CombinedChlorinePane(context, model)
  HBox.setHgrow(combinedChlorinePane, Priority.Always)

  val phPane = PhPane(context, model)
  HBox.setHgrow(phPane, Priority.Always)

  val calciumHardnessPane = CalciumHardnessPane(context, model)
  HBox.setHgrow(calciumHardnessPane, Priority.Always)

  val totalAlkalinityPane = TotalAlkalinityPane(context, model)
  HBox.setHgrow(totalAlkalinityPane, Priority.Always)

  val cyanuricAcidPane = CyanuricAcidPane(context, model)
  HBox.setHgrow(cyanuricAcidPane, Priority.Always)

  val totalBrominePane = TotalBrominePane(context, model)
  HBox.setHgrow(totalBrominePane, Priority.Always)

  val saltPane = SaltPane(context, model)
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