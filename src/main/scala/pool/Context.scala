package pool

import com.typesafe.config.{Config, ConfigFactory}
import com.zaxxer.hikari.HikariDataSource

import javax.sql.DataSource

import scalafx.scene.image.{Image, ImageView}

final class Context(config: Config):
  val windowTitle = config.getString("window.title")
  val windowWidth = config.getDouble("window.width")
  val windowHeight = config.getDouble("window.height")

  val url = config.getString("db.url")
  val user = config.getString("db.user")
  val password = config.getString("db.password")
  val dataSourceClassName = config.getString("db.dataSourceClassName")
  val maximumPoolSize = config.getInt("db.maximumPoolSize")

  val dataSource: DataSource = {
    val ds = new HikariDataSource()
    ds.setDataSourceClassName(dataSourceClassName)
    ds.addDataSourceProperty("url", url)
    ds.addDataSourceProperty("user", user)
    ds.addDataSourceProperty("password", password)
    ds.setMaximumPoolSize(maximumPoolSize)
    ds
  }

  val logo = new Image(Image.getClass.getResourceAsStream("/logo.white.png"))
  val addImage = loadImageView("/add.png")
  val editImage = loadImageView("/edit.png")

  val store = Store(this)
  val model = Model(this)

  private def loadImageView(path: String): ImageView = new ImageView {
    image = new Image(Image.getClass.getResourceAsStream(path))
    fitHeight = 25
    fitWidth = 25
    preserveRatio = true
    smooth = true
  }