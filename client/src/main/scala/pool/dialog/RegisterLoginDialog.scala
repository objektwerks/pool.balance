package pool.dialog

import scalafx.Includes.*
import scalafx.scene.layout.VBox
import scalafx.scene.control.{ButtonType, Dialog, TextField, TitledPane}
import scalafx.scene.control.ButtonBar.ButtonData
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

  val registerControls = List[(String, TextField)](
    context.labelEmailAddress -> registerEmailAddressTextField
  )

  val registerTitledPane = new TitledPane:
    collapsible = false
    maxWidth = Double.MaxValue
    maxHeight = Double.MaxValue
    content = ControlGridPane(registerControls)

  val loginEmailAddressTextField = new TextField:
    text = registerLogin.loginEmailAddress

  val loginPinTextField = new TextField:
    text = registerLogin.loginPin

  val loginControls = List[(String, TextField)](
    context.labelEmailAddress -> loginEmailAddressTextField,
    context.labelPin -> loginPinTextField
  )

  val loginTitledPane = new TitledPane:
    collapsible = false
    maxWidth = Double.MaxValue
    maxHeight = Double.MaxValue
    content = ControlGridPane(loginControls)

  val registerLoginPane = new VBox:
    spacing = 3
    children = List(registerTitledPane, loginTitledPane)

  dialogPane().content = registerLoginPane

  val registerButtonType = new ButtonType("Register", ButtonData.Left)
  val loginButtonType = new ButtonType("Login", ButtonData.Right)

  dialogPane().buttonTypes = List(registerButtonType, loginButtonType)