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

  val paneAdd = config.getString("pane.add")
  val paneEdit = config.getString("pane.edit")

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
  val labelCombinedChlorine = config.getString("label.combinedChlorine")
  val labelTotalChlorine = config.getString("label.totalChlorine")
  val labelPh = config.getString("label.ph")
  val labelCalciumHardness = config.getString("label.calciumHardness")
  val labelTotalAlkalinity = config.getString("label.totalAlkalinity")
  val labelCyanuricAcid = config.getString("label.cyanuricAcid")
  val labelTotalBromine = config.getString("label.totalBromine")
  val labelTemperature = config.getString("label.temperature")
  val labelMeasure = config.getString("label.measured")
  val labelTypeof = config.getString("label.typeof")
  val labelAmount = config.getString("label.amount")
  val labelAdded = config.getString("label.added")
  val labelCurrent = config.getString("label.current")
  val labelAverage = config.getString("label.average")

  val tableName = config.getString("table.name")
  val tableBuilt = config.getString("table.built")
  val tableVolume = config.getString("table.volume")
  val tableUnit = config.getString("table.unit")
  val tableBrush = config.getString("table.brush")
  val tableNet = config.getString("table.net")
  val tableSkimmerBasket = config.getString("table.skimmerBasket")
  val tablePumpBasket = config.getString("table.pumpBasket")
  val tablePumpFilter = config.getString("table.pumpFilter")
  val tableVacuum = config.getString("table.vacuum")
  val tableCleaned = config.getString("table.cleaned")
  val tableFreeChlorine = config.getString("table.freeChlorine")
  val tableCombinedChlorine = config.getString("table.combinedChlorine")
  val tableTotalChlorine = config.getString("table.totalChlorine")
  val tablePh = config.getString("table.ph")
  val tableCalciumHardness = config.getString("table.calciumHardness")
  val tableTotalAlkalinity = config.getString("table.totalAlkalinity")
  val tableCyanuricAcid = config.getString("table.cyanuricAcid")
  val tableTotalBromine = config.getString("table.totalBromine")
  val tableTemperature = config.getString("table.temperature")
  val tableMeasured = config.getString("table.measured")
  val tableTypeof = config.getString("table.typeof")
  val tableAmount = config.getString("table.amount")
  val tableAdded = config.getString("table.added")

  val buttonSave = config.getString("button.save")

  val dialogPool = config.getString("dialog.pool")
  val dialogCleaning = config.getString("dialog.cleaning")
  val dialogMeasurement = config.getString("dialog.measurement")
  val dialogChemical = config.getString("dialog.chemical")

  val tabCleanings = config.getString("tab.cleanings")
  val tabMeasurements = config.getString("tab.measurements")
  val tabChemicals = config.getString("tab.chemicals")

  val listUnits = config.getStringList("list.units").asScala.toList
  val listPoolUnits = config.getStringList("list.poolUnits").asScala.toList
  val listChemicals = config.getStringList("list.chemicals").asScala.toList

  def addImage = loadImageView("/add.png")
  def editImage = loadImageView("/edit.png")

  def logo = new Image(Image.getClass.getResourceAsStream("/logo.png"))

  val store = Store(this)
  val model = Model(this)

  private def loadImageView(path: String): ImageView = new ImageView {
    image = new Image(Image.getClass.getResourceAsStream(path))
    fitHeight = 25
    fitWidth = 25
    preserveRatio = true
    smooth = true
  }