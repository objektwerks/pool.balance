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
2. copy unzipped poolcalc-${version} directory to **new** ${poolcalc.directory}
3. set executable permissions on ${poolcalc.directory}/poolcalc-${version}/bin/poolcalc

Execute
-------
1. execute ${poolcalc-directory}/poolcalc-${version}/bin/poolcalc

Measurements
------------
>Measured in ppm ( parts per million ).
1. total chlorine (tc = fc + cc): 0 - 10, ok = 1 - 5, ideal = 3
2. free chlorine (fc): 0 - 10, ok = 1 - 5, ideal = 3
3. combined chlorine (cc = tc - fc): 0 - 0.5, ok = 0.2, ideal = 0
4. ph: 6.2 - 8.4, ok = 7.2 - 7.6, ideal = 7.4
5. calcium hardness (ch): 0 - 1000, ok = 250 - 500, ideal = 375
6. total alkalinity (ta): 0 - 240, ok = 80 - 120, ideal = 100
7. cyanuric acid (cya): 0 - 300, ok = 30 - 100, ideal = 50
8. total bromine (tb): 0 - 20, ok = 2 - 10, ideal = 5
9. temp: 0 - 100

Resources
---------
1. [JavaFX](https://openjfx.io/index.html)
2. [ScalaFX](http://www.scalafx.org/)