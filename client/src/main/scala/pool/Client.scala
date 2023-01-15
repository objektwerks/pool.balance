package pool

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

import pool.{Login, Register}
import pool.dialog.RegisterLogin
import pool.dialog.RegisterLoginDialog

object Client extends JFXApp3 with LazyLogging:
  private val conf = ConfigFactory.load("client.conf")
  private val url = conf.getString("url")
  private val fetcher = Fetcher(url)
  private val model = Model(fetcher)
  private val context = Context(conf, model)

  override def start(): Unit =
    val view = View(context)
    stage = new JFXApp3.PrimaryStage:
      scene = view.scene
      title = context.windowTitle
      minWidth = context.windowWidth
      minHeight = context.windowHeight
      icons.add(context.logo)
    
    RegisterLoginDialog(stage, context).showAndWait() match
      case Some(register: Register) => model.register(register)
      case Some(login: Login) => model.login(login)
      case _ => System.exit(-1)
    

    logger.info(s"Client started targeting: $url")

  override def stopApp(): Unit = logger.info("Client stopped.")