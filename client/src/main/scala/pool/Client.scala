package pool

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

object Client extends JFXApp3 with LazyLogging:
  private val context = Context( ConfigFactory.load("app.conf") )

  override def start(): Unit =    
    stage = new JFXApp3.PrimaryStage:
      scene = context.view.scene
      title = context.windowTitle
      minWidth = context.windowWidth
      minHeight = context.windowHeight
      icons.add(context.logo)

    logger.info("Client started.")

  override def stopApp(): Unit = logger.info("Client stopped.")