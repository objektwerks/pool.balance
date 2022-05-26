package objektwerks

import com.typesafe.config.ConfigFactory

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

object App extends JFXApp3:
  private val conf = ConfigFactory.load("app.conf")
  private val windowTitle = conf.getString("window.title")
  private val windowWidth = conf.getInt("window.width").toDouble
  private val windowHeight = conf.getInt("window.height").toDouble

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