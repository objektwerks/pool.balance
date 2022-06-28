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

  val buttonAdd = config.getString("button.add")
  val buttonEdit = config.getString("button.edit")
  val buttonSave = config.getString("button.save")
  val buttonChart = config.getString("button.chart")

  val chartChemicals = config.getString("chart.chemicals")
  val chartMeasurements = config.getString("chart.measurements")
  val chartYearMonth = config.getString("chart.yearMonth")
  val chartMin = config.getString("chart.min")
  val chartMax = config.getString("chart.max")
  val chartAvg = config.getString("chart.avg")
  val chartTotalChlorine = config.getString("chart.totalChlorine")
  val chartFreeChlorine = config.getString("chart.freeChlorine")
  val chartCombinedChlorine = config.getString("chart.combinedChlorine")
  val chartPh = config.getString("chart.ph")
  val chartCalciumHardness = config.getString("chart.calciumHardness")
  val chartTotalAlkalinity = config.getString("chart.totalAlkalinity")
  val chartCyanuricAcid = config.getString("chart.cyanuricAcid")
  val chartTotalBromine = config.getString("chart.totalBromine")
  val chartSalt = config.getString("chart.salt")

  val dialogPool = config.getString("dialog.pool")
  val dialogCleaning = config.getString("dialog.cleaning")
  val dialogMeasurement = config.getString("dialog.measurement")
  val dialogChemical = config.getString("dialog.chemical")

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
  val labelTotalChlorine = config.getString("label.totalChlorine")
  val labelFreeChlorine = config.getString("label.freeChlorine")
  val labelCombinedChlorine = config.getString("label.combinedChlorine")
  val labelPh = config.getString("label.ph")
  val labelCalciumHardness = config.getString("label.calciumHardness")
  val labelTotalAlkalinity = config.getString("label.totalAlkalinity")
  val labelCyanuricAcid = config.getString("label.cyanuricAcid")
  val labelTotalBromine = config.getString("label.totalBromine")
  val labelSalt = config.getString("label.salt")
  val labelTemperature = config.getString("label.temperature")
  val labelMeasure = config.getString("label.measured")
  val labelTypeof = config.getString("label.typeof")
  val labelAmount = config.getString("label.amount")
  val labelAdded = config.getString("label.added")
  val labelCurrent = config.getString("label.current")
  val labelAverage = config.getString("label.average")

  val headerName = config.getString("header.name")
  val headerBuilt = config.getString("header.built")
  val headerVolume = config.getString("header.volume")
  val headerUnit = config.getString("header.unit")
  val headerBrush = config.getString("header.brush")
  val headerNet = config.getString("header.net")
  val headerSkimmerBasket = config.getString("header.skimmerBasket")
  val headerPumpBasket = config.getString("header.pumpBasket")
  val headerPumpFilter = config.getString("header.pumpFilter")
  val headerVacuum = config.getString("header.vacuum")
  val headerCleaned = config.getString("header.cleaned")
  val headerTotalChlorine = config.getString("header.totalChlorine")
  val headerFreeChlorine = config.getString("header.freeChlorine")
  val headerCombinedChlorine = config.getString("header.combinedChlorine")
  val headerPh = config.getString("header.ph")
  val headerCalciumHardness = config.getString("header.calciumHardness")
  val headerTotalAlkalinity = config.getString("header.totalAlkalinity")
  val headerCyanuricAcid = config.getString("header.cyanuricAcid")
  val headerTotalBromine = config.getString("header.totalBromine")
  val headerSalt = config.getString("header.salt")
  val headerTemperature = config.getString("header.temperature")
  val headerMeasured = config.getString("header.measured")
  val headerTypeof = config.getString("header.typeof")
  val headerAmount = config.getString("header.amount")
  val headerAdded = config.getString("header.added")

  val tabCleanings = config.getString("tab.cleanings")
  val tabMeasurements = config.getString("tab.measurements")
  val tabChemicals = config.getString("tab.chemicals")

  def addImage = loadImageView("/add.png")
  def editImage = loadImageView("/edit.png")
  def chartImage = loadImageView("/chart.png")

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