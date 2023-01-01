Pool Balance
------------
>Pool cleaning, measurement and chemical balancing app using ScalaFX and Scala 3.

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

Execute
-------
>To execute an assembled jar locally:
1. java -jar .assembly/pool-balance-mac-0.3.jar
2. java -jar .assembly/pool-balance-m1-0.3.jar
3. java -jar .assembly/pool-balance-win-0.3.jar
4. java -jar .assembly/pool-balance-linux-0.3.jar

Deploy
------
>Consider these options:
1. [jDeploy](https://www.npmjs.com/package/jdeploy)
2. [Conveyor](https://hydraulic.software/index.html)

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

| Measurement                       | Range       | Good        | Ideal |
| --------------------------------- | ----------- | ----------- | ----- |
| total chlorine (tc = fc + cc)     | 0 - 10      | 1 - 5       | 3     |
| free chlorine (fc)                | 0 - 10      | 1 - 5       | 3     |
| combinded chlorine (cc = tc - fc) | 0.0 - 0.5   | 0.0 - 0.2   | 0.0   |
| ph                                | 6.2 - 8.4   | 7.2 - 7.6   | 7.4   |
| calcium hardness                  | 0 - 1000    | 250 - 500   | 375   |
| total alkalinity                  | 0 - 240     | 80 - 120    | 100   |
| cyanuric acid                     | 0 - 300     | 30 - 100    | 50    |
| total bromine                     | 0 - 20      | 2 - 10      | 5     |
| salt                              | 0 - 3600    | 2700 - 3400 | 3200  |
| temperature                       | 50 - 100    | 75 - 85     | 82    |

Chemicals
---------
* Liquids measured in: gallons ( gl ) and liters ( l ).
* Granules measured in: pounds ( lbs ) and kilograms ( kg ).
1. LiquidChlorine ( gl/l )
2. Trichlor ( tablet )
3. Dichlor ( lbs/kg )
4. CalciumHypochlorite ( lbs/kg )
5. Stabilizer ( lbs/kg )
6. Algaecide ( gl/l )
7. MuriaticAcid ( gl/l )
8. Salt ( lbs/kg )

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
* cleanings - line chart ( x = cleaned, y = ? )
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
>Copyright (c) [2022, 2023] [Objektwerks]

>Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    * http://www.apache.org/licenses/LICENSE-2.0

>Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
