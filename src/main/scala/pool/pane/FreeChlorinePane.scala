package pool.pane

import pool.Context

class FreeChlorinePane(context: Context) extends DashboardTitledPane(context):
  title.text = context.tableFreeChlorine