package pool.dialog

import scalafx.Includes.*
import scalafx.scene.layout.VBox
import scalafx.scene.control.{ButtonType, Dialog, TextField, TitledPane}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.stage.Stage

import pool.{Context, Register, Login}
import pool.Validator.*

final case class RegisterLogin(register: Option[Register] = None,
                               login: Option[Login] = None)

final class RegisterLoginDialog(primaryStage: Stage, context: Context) extends Dialog[RegisterLogin]:
  initOwner(primaryStage)
  title = context.windowTitle
  headerText = context.dialogRegisterLogin
  graphic = context.logoImage

  val registerEmailAddressTextField = new TextField
  val registerControls = List[(String, TextField)](
    context.labelEmailAddress -> registerEmailAddressTextField
  )
  val registerTitledPane = new TitledPane:
    collapsible = false
    maxWidth = Double.MaxValue
    maxHeight = Double.MaxValue
    content = ControlGridPane(registerControls)

  val loginEmailAddressTextField = new TextField
  val loginPinTextField = new TextField:
    prefColumnCount = 7
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
    spacing = 6
    children = List(registerTitledPane, loginTitledPane)

  dialogPane().content = registerLoginPane

  val registerButtonType = new ButtonType(context.buttonRegister, ButtonData.Left)
  val registerButton = dialogPane().lookupButton(registerButtonType)
  registerButton.disable = true

  val loginButtonType = new ButtonType(context.buttonLogin, ButtonData.Right)
  val loginButton = dialogPane().lookupButton(loginButtonType)
  loginButton.disable = true

  dialogPane().buttonTypes = List(registerButtonType, loginButtonType)

  resultConverter = dialogButton => {
    if dialogButton == registerButtonType then
      RegisterLogin(register = Some( Register( registerEmailAddressTextField.text.value ) ) )
    else if dialogButton == loginButtonType then
      RegisterLogin(login = Some( Login( loginEmailAddressTextField.text.value, loginPinTextField.text.value ) ) )
    else null
  }

  registerEmailAddressTextField.text.onChange { (_, _, newValue) =>
    registerButton.disable = !newValue.isEmailAddress
  }

  loginEmailAddressTextField.text.onChange { (_, _, newValue) =>
    loginButton.disable = !newValue.isEmailAddress || !loginPinTextField.text.value.isPin
  }

  loginPinTextField.text.onChange { (_, _, newValue) =>
    loginButton.disable = !newValue.isPin || !loginEmailAddressTextField.text.value.isEmailAddress
  }