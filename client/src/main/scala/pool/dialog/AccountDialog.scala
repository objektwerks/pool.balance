package pool.dialog

import scalafx.scene.control.Dialog

import pool.{Account, Context}

final class AccountDialog(context: Context, account: Account) extends Dialog[Account]:
  