package pool

import com.typesafe.config.{Config, ConfigFactory}
import com.zaxxer.hikari.HikariDataSource

import javax.sql.DataSource

import scala.jdk.CollectionConverters.*
import scalafx.scene.image.{Image, ImageView}

final class Context(config: Config):
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

  val windowTitle = config.getString("window.title")
  val windowWidth = config.getDouble("window.width")
  val windowHeight = config.getDouble("window.height")

  val labelPools = config.getString("label.pools")
  val labelCleanings = config.getString("label.cleanings")
  val labelMeasurements = config.getString("label.measurements")
  val labelChemicals = config.getString("label.chemicals")
  val labelName = config.getString("label.name")
  val labelBuilt = config.getString("label.built")
  val labelVolume = config.getString("label.volume")
  val labelUnit = config.getString("label.unit")
  val labelBrush = config.getString("label.brush")
  val labelNet = config.getString("label.net")
  val labelSkimmerBasket = config.getString("label.skimmerBasket")
  val labelPumpBasket = config.getString("label.pumpBasket")
  val labelPumpFilter = config.getString("label.pumpFilter")
  val labelVacuum = config.getString("label.vacuum")
  val labelCleaned = config.getString("label.cleaned")
  val labelFreeChlorine = config.getString("label.freeChlorine")
  val labelCalciumHardness = config.getString("label.calciumHardness")
  val labelTotalAlkalinity = config.getString("label.totalAlkalinity")
  val labelCyanuricAcid = config.getString("label.cyanuricAcid")
  val labelTotalBromine = config.getString("label.totalBromine")
  val labelTemperature = config.getString("label.temperature")
  val labelMeasure = config.getString("label.measured")
  val labelTypeof = config.getString("label.typeof")
  val labelAmount = config.getString("label.amount")
  val labelAdded = config.getString("label.added")

  val buttonSave = config.getString("button.save")

  val dialogHeaderPool = config.getString("dialog.header.pool")
  val dialogHeaderCleaning = config.getString("dialog.header.cleaning")
  val dialogHeaderMeasurement = config.getString("dialog.header.measurement")
  val dialogHeaderChemical = config.getString("dialog.header.chemical")

  val listUnits = config.getStringList("list.units").asScala
  val listChemicals = config.getStringList("list.chemicals").asScala

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