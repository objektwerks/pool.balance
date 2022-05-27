package pool

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

object App extends JFXApp3 with LazyLogging:
  override def start(): Unit =
    val conf = Conf( ConfigFactory.load("app.conf") )
    val store = Store(conf)
    val model = Model(conf, store)
    val view = View(conf, model)

    stage = new JFXApp3.PrimaryStage {
      scene = view.scene
      title = conf.windowTitle
      minWidth = conf.windowWidth
      minHeight = conf.windowHeight
      icons.add(conf.logo)
    }
    
    logger.info("App started.")

  override def stopApp(): Unit =
    logger.info("App stopped.")