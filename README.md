Pool Balance
------------
>Pool cleaning, measurement and balancing app using ScalaFX and Scala 3.

Todo
----
1. Bind errors to status pane.
2. Consider dashboard pass and fail badge.

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test

Run
---
1. sbt run

Assembly
--------
>To build for a "mac", "m1', "win" or "linux" os target:
1. sbt -Dtarget="mac" clean test assembly copyAssemblyJar
2. sbt -Dtarget="m1" clean test assembly copyAssemblyJar
3. sbt -Dtarget="win" clean test assembly copyAssemblyJar
4. sbt -Dtarget="linux" clean test assembly copyAssemblyJar
>To run locally:
1. java -jar .assembly/pool-balance-mac-0.1.jar
2. java -jar .assembly/pool-balance-m1-0.1.jar
3. java -jar .assembly/pool-balance-win-0.1.jar
4. java -jar .assembly/pool-balance-linux-0.1.jar

Features
--------
1. dashboard
2. cleanings
3. measurements
4. chemicals
5. charts
6. database

Use Cases
---------
1. clean pool and components
2. measure pool chemical content
3. add chemicals to pool

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
9. temperature: 50 - 100

Chemicals
---------
* LiquidChlorine, Trichlor, Dichlor, CalciumHypochlorite, Stabilizer, Algaecide

Entity
------
* Pool 1 --> * Cleaning | Measurement | Chemical

Model
-----
* App 1 --> 1 Context | Store | Model | View
* Store 1 --> 1 Context
* Model 1 --> 1 Context | Store
* View 1 --> 1 Context | Model

UI
--
1. Top: dashboard(total chlorine, free chlorine, ph, calcium hardness, total alkalinity, cyanuric acid, total bromine)
2. Left: pane(pools)
3. Center: tabbedpane(cleanings, measurements, chemicals)

Charts
------
* cleanings - line chart ( x = cleaned, y = typeof )
* measurements - line chart ( x = measured, y = measurement )
* chemicals - bar chart ( x = added, y = amount/typeof )

Resources
---------
1. [JavaFX](https://openjfx.io/index.html)
2. [ScalaFX](http://www.scalafx.org/)
3. [ScalikeJdbc](http://scalikejdbc.org/)
4. [jDeploy](https://www.jdeploy.com/)
5. [JavaFX Tutorial](https://jenkov.com/tutorials/javafx/index.html)

License
-------
> Copyright (c) [2022] [Objektwerks]

>Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    * http://www.apache.org/licenses/LICENSE-2.0

>Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.