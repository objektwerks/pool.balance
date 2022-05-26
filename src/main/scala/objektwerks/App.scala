package objektwerks

import com.typesafe.scalalogging.LazyLogging

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

import Conf.*

object App extends JFXApp3 with LazyLogging:
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
      icons.add(Conf.logo)
    }
    logger.info("App started.")

  override def stopApp(): Unit =
    logger.info("App stopped.")