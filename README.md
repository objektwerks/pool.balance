Pool Calc
---------
>Pool calculator.

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test

Run
---
1. sbt run

Package
-------
1. sbt clean test universal:packageBin
2. verify ./target/universal/poolcalc-${version}.zip

Install
-------
1. unzip ./target/universal/poolcalc-${version}.zip
2. copy unzipped poolcalc-${version} directory to new ${poolcalc.directory}
3. set executable permissions on ${poolcalc.directory}/poolcalc-${version}/bin/poolcalc

Execute
-------
1. execute ${poolcalc-directory}/poolcalc-${version}/bin/poolcalc

Resources
---------
1. [JavaFX](https://openjfx.io/index.html)
2. [ScalaFX](http://www.scalafx.org/)