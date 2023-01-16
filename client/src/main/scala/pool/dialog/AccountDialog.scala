package pool.dialog

import scalafx.scene.control.{Dialog, Label}

import pool.{Account, Client, Context}

final class AccountDialog(context: Context, account: Account) extends Dialog[Account]:
  initOwner(Client.stage)
  title = context.windowTitle
  headerText = context.dialogAccount

  val controls = List[(String, Label)](
    context.labelLicense -> Label( account.license ),
    context.labelEmailAddress -> Label( account.emailAddress ),
    context.labelPin -> Label( account.pin ),
    context.labelActivated -> Label( account.activated ),
    context.labelDeactivated -> Label( account.deactivated )
  )