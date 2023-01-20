package pool

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scalafx.Includes.*
import scalafx.application.JFXApp3

import pool.{Login, Register}
import pool.dialog.{Alerts, RegisterLogin, RegisterLoginDialog}

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

    model.registered.onChange { (_, _, _) =>
      Alerts.showRegisterAlert(context, stage)
      logger.info("Register failed. Client stopping ...")
      sys.exit(-1)
    }

    model.loggedin.onChange { (_, _, _) =>
      Alerts.showLoginAlert(context, stage)
      logger.info("Login failed. Client stopping ...")
      sys.exit(-1)
    }
    
    RegisterLoginDialog(stage, context).showAndWait() match
      case Some( RegisterLogin( Some(register), None) ) => model.register(register)
      case Some( RegisterLogin( None, Some(login) ) ) => model.login(login)
      case _ =>
        logger.info("Register or login failed.")
        sys.exit(-1)
    
    logger.info(s"Client started targeting: $url")

  override def stopApp(): Unit = logger.info("Client stopped.")