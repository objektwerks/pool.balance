package objektwerks

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

import Conf.*

object App extends JFXApp3:
  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage {
      scene = new Scene {
        root = new VBox {
          prefWidth = windowWidth
          prefHeight = windowHeight
          spacing = 6
          padding = Insets(6)
          children = List()
        }
      }
      title = windowTitle
      minWidth = windowWidth
      minHeight = windowHeight
      icons.add(Images.logo)
    }

  override def stopApp(): Unit =
    super.stopApp()