package pool.dialog

import scalafx.scene.control.Dialog
import scalafx.stage.Stage

import pool.Context

final case class RegisterLogin(registerEmailAddress: Option[String],
                               loginEmailAddress: Option[String],
                               loginPin: Option[String])

final class RegisterLoginDialog(owner: Stage, context: Context) extends Dialog[RegisterLogin]:
  initOwner(owner)
  title = context.windowTitle
  headerText = context.dialogRegisterLogin