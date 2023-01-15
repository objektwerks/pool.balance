package pool.dialog

import scalafx.scene.control.{Dialog, TextField, TitledPane}
import scalafx.stage.Stage

import pool.Context

final case class RegisterLogin(registerEmailAddress: String = "",
                               loginEmailAddress: String = "",
                               loginPin: String = "")

final class RegisterLoginDialog(owner: Stage, context: Context) extends Dialog[RegisterLogin]:
  initOwner(owner)
  title = context.windowTitle
  headerText = context.dialogRegisterLogin

  val registerLogin = RegisterLogin()

  val registerEmailAddressTextField = new TextField:
    text = registerLogin.registerEmailAddress

  val loginEmailAddressTextField = new TextField:
    text = registerLogin.loginEmailAddress

  val loginPinTextField = new TextField:
    text = registerLogin.loginPin

  val registerControls = List[(String, TextField)](
    "Email Address:" -> registerEmailAddressTextField
  )

  val registerTitledPane = new TitledPane:
    collapsible = false
    maxWidth = Double.MaxValue
    maxHeight = Double.MaxValue

  val loginTitledPane = new TitledPane:
    collapsible = false
    maxWidth = Double.MaxValue
    maxHeight = Double.MaxValue
