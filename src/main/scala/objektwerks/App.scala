package objektwerks

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle

object App extends JFXApp3:
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title.value = "Pool Calc"
      width = 600
      height = 400
      scene = new Scene {
        content = new Rectangle {}
      }
    }
  }