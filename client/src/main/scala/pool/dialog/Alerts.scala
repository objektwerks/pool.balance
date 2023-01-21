package pool.dialog

import scalafx.application.Platform
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.control.Alert.AlertType
import scalafx.stage.Stage

import pool.Context

object Alerts:
  def showServerAlert(context: Context, stage: Stage): Option[ButtonType] =
    new Alert(AlertType.Error) {
      initOwner(stage)
      title = context.windowTitle
      headerText = context.buttonRegister
      contentText = context.errorRegister
    }.showAndWait()

  def showRegisterAlert(context: Context, stage: Stage): Option[ButtonType] =
    new Alert(AlertType.Error) {
      initOwner(stage)
      title = context.windowTitle
      headerText = context.buttonRegister
      contentText = context.errorRegister
    }.showAndWait()

  def showLoginAlert(context: Context, stage: Stage): Option[ButtonType] =
    new Alert(AlertType.Error) {
      initOwner(stage)
      title = context.windowTitle
      headerText = context.buttonLogin
      contentText = context.errorLogin
    }.showAndWait()
