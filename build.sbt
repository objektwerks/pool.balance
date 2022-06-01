name := "pool.balance"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.1.3-RC4"
mainClass := Some("pool.App")
libraryDependencies ++= {
  Seq(
    "org.scalafx" %% "scalafx" % "18.0.1-R27",
    "org.jfxtras" % "jfxtras-controls" % "17-r1",
    "org.scalikejdbc" %% "scalikejdbc" % "4.0.0",
    "com.zaxxer" % "HikariCP" % "5.0.1" exclude("org.slf4j", "slf4j-api"),
    "com.h2database" % "h2" % "2.1.212",
    "com.typesafe" % "config" % "1.4.2",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    "ch.qos.logback" % "logback-classic" % "1.2.11",
    "org.scalatest" %% "scalatest" % "3.2.12" % Test
  )
}

/*
1. sbt -Dtarget="mac" clean test assembly
2. sbt -Dtarget="m1" clean test assembly
3. sbt -Dtarget="win" clean test assembly
*/
lazy val os = System.getProperty("target") match {
  case name if name.startsWith("mac") => "mac"
  case name if name.startsWith("m1") => "mac-aarch64"
  case name if name.startsWith("win") => "win"
  case _ => throw new Exception("Only Apple M1 and Windows supported for this build.")
}
if (os == "mac-aarch64") assemblyJarName := "pool-balance-m1-0.1.jar"
else assemblyJarName := "pool-balance-win-0.1.jar"

lazy val javafxModules = Seq("base", "controls", "web")
libraryDependencies ++= javafxModules.map( module =>
  "org.openjfx" % s"javafx-$module" % "18.0.1" classifier os
)

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

/*
I need to run my sbt clean test assembly builds for a target os. 
I work on an Apple M1, but I need to also target Windows as well. 
To date, I've come up with solution: ```https://github.com/objektwerks/pool.balance/blob/main/build.sbt```, which works.
Unpacking both the m1 and win jars that I create, I only noticed 1 difference: ```jfxwebkit```, which is found in the win jar.
I thought that was interesting.
*/