package pool.dialog

import scalafx.scene.control.Dialog

import pool.{App, Context}

class ConverterDialog(context: Context) extends Dialog[Number]:
  initOwner(App.stage)
  title = context.windowTitle
  headerText = context.dialogConverters