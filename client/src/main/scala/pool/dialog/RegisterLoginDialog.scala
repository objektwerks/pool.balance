package pool.dialog

import scalafx.scene.control.Dialog

import pool.Account

final case class RegisterLogin(registerEmailAddress: Option[String],
                               loginEmailAddress: Option[String],
                               loginPin: Option[String])

final class RegisterLoginDialog extends Dialog[RegisterLogin]:
  