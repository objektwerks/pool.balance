package pool.dialog

import scalafx.scene.control.{Dialog, Label}

import pool.{Account, Client, Context}

final class AccountDialog(context: Context, account: Account) extends Dialog[Account]:
  initOwner(Client.stage)
  title = context.windowTitle
  headerText = context.dialogAccount

  val controls = List[(String, Label)](
    "License:" -> Label( account.license ),
    "Email Address:" -> Label( account.emailAddress ),
    "Pin:" -> Label( account.pin ),
    "Activated:" -> Label( account.activated ),
    "Deactivated:" -> Label( account.deactivated )
  )