package pool.dashboard

import pool.Context

class SaltPane(context: Context) extends DashboardTitledPane(context):
  text = context.headerSalt
  current.text <== context.model.currentSalt.asString
  average.text <== context.model.averageSalt.asString