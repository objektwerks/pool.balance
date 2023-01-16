package pool.dialog

import scalafx.Includes.*
import scalafx.scene.control.{ButtonType, Dialog, Label}
import scalafx.scene.control.ButtonBar.ButtonData

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

  dialogPane().content = ControlGridPane(controls)

  val activateButtonType = new ButtonType(context.buttonActivate, ButtonData.OKDone)
  val deactivateButtonType = new ButtonType(context.buttonDeactivate, ButtonData.OKDone)
  dialogPane().buttonTypes = List(ButtonType.Close, activateButtonType, deactivateButtonType)