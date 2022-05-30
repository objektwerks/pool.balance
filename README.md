Pool Balance
------------
>Pool chemical measurement and charting app using ScalaFX and Scala 3.

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
1. sbt assembly
>See: target/scala-3.1.3-RC3/pool-balance-0.1.jar

Features
--------
1. dashboard
2. measurements
3. chemicals
4. charts
5. database

Use Cases
---------
1. measure pool water chemical content
2. balance pool water chemical content

Tests
-----
>Measured in ppm ( parts per million ).
1. free chlorine (fc): 0 - 10, ok = 1 - 5, ideal = 3
2. combined chlorine (cc = tc - fc): 0 - 0.5, ok = 0.2, ideal = 0
3. total chlorine (tc = fc + cc): 0 - 10, ok = 1 - 5, ideal = 3
4. ph: 6.2 - 8.4, ok = 7.2 - 7.6, ideal = 7.4
5. calcium hardness (ch): 0 - 1000, ok = 250 - 500, ideal = 375
6. total alkalinity (ta): 0 - 240, ok = 80 - 120, ideal = 100
7. cyanuric acid (cya): 0 - 300, ok = 30 - 100, ideal = 50
8. total bromine (tb): 0 - 20, ok = 2 - 10, ideal = 5
9. temperature: 0 - 100

Chemicals
---------
1. Liquid Chlorine
2. Trichlor
3. Dichlor
4. Calcium Hypochlorite
5. Stabilizer

Entities
--------
1. Pool
2. FreeChlorine
3. CombinedChlorine
4. TotalChlorine
5. pH
6. CalciumHardness
7. TotalAlkalinity
8. CyanuricAcid
9. TotalBromine
10. Temperature

UI
--
* App 1 --> 1 Context | Store | Model | View
* Store 1 --> 1 Context
* Model 1 --> 1 Context | Store
* View 1 --> 1 Context | Model

Resources
---------
1. [JavaFX](https://openjfx.io/index.html)
2. [ScalaFX](http://www.scalafx.org/)

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