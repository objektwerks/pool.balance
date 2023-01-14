package pool

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

object Client extends JFXApp3 with LazyLogging:
  private val conf = ConfigFactory.load("client.conf")
  private val url = conf.getString("url")
  private val fetcher = Fetcher(url)
  private val model = Model(fetcher)
  private val context = Context(conf, model)

  override def start(): Unit =
    val view = View(context)
    // TODO Register-Login Dialog
    stage = new JFXApp3.PrimaryStage:
      scene = view.scene
      title = context.windowTitle
      minWidth = context.windowWidth
      minHeight = context.windowHeight
      icons.add(context.logo)

    logger.info(s"Client started targeting: $url")

  override def stopApp(): Unit = logger.info("Client stopped.")