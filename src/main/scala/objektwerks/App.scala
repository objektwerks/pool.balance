package objektwerks

import com.typesafe.config.ConfigFactory

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import scalafx.Includes._

object App extends JFXApp3:
  val conf = ConfigFactory.load("app.conf")

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage {
      val sceneGraph = new Scene {}
      title = conf.getString("title")
      minHeight = conf.getInt("height").toDouble
      minWidth = conf.getInt("width").toDouble
      icons.add(Images.logo)
    }
  
  sys.addShutdownHook {
  }