package pool.dialog

import scalafx.scene.control.Dialog
import scalafx.stage.Stage

final case class RegisterLogin(registerEmailAddress: Option[String],
                               loginEmailAddress: Option[String],
                               loginPin: Option[String])

final class RegisterLoginDialog(owner: Stage) extends Dialog[RegisterLogin]:
  initOwner(owner)