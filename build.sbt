name := "pool.balance"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.1.2"
libraryDependencies ++= {
  Seq(
    "org.scalafx" %% "scalafx" % "18.0.1-R27",
    "org.scalikejdbc" %% "scalikejdbc" % "4.0.0",
    "com.h2database" % "h2" % "2.1.212",
    "com.typesafe" % "config" % "1.4.2",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    "ch.qos.logback" % "logback-classic" % "1.2.11",
    "org.scalatest" %% "scalatest" % "3.2.12" % Test
  )
}
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
lazy val javaFXModules = Seq("base", "controls", "graphics", "media", "web")
libraryDependencies ++= javaFXModules.map( module =>
  "org.openjfx" % s"javafx-$module" % "18" classifier osName
)