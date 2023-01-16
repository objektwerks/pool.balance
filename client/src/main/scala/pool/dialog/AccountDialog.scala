package pool.dialog

import scalafx.scene.control.Dialog

import pool.{Account, Client, Context}

final class AccountDialog(context: Context, account: Account) extends Dialog[Account]:
  initOwner(Client.stage)
  title = context.windowTitle
  headerText = context.dialogPool