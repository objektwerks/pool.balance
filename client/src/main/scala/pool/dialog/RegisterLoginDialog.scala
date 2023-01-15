package pool.dialog

import scalafx.scene.control.Dialog

import pool.Account

final case class RegisterLogin(emailAddress: String, pin: Option[String])

final class RegisterLoginDialog extends Dialog[RegisterLogin]:
  