package objektwerks

import com.typesafe.config.ConfigFactory

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import scalafx.Includes._

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.SplitPane
import scalafx.scene.layout.{Priority, VBox}

object App extends JFXApp3:
  val conf = ConfigFactory.load("app.conf")

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage {
      scene = new Scene {
        root = new VBox {
          prefHeight = 600
          prefWidth = 800
          spacing = 6
          padding = Insets(6)
          children = List()
        }
      }
      title = conf.getString("title")
      minHeight = conf.getInt("height").toDouble
      minWidth = conf.getInt("width").toDouble
      icons.add(Images.logo)
    }
  
  sys.addShutdownHook {
  }