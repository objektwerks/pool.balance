Pool Balance
------------
>Pool chemical measurement and charting app.

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test

Run
---
1. sbt run

Features
--------
1. dashboard
2. measurements
3. actions
4. charts
5. database

Measurements
------------
>Measured in ppm ( parts per million ).
1. free chlorine (fc): 0 - 10, ok = 1 - 5, ideal = 3
2. combined chlorine (cc = tc - fc): 0 - 0.5, ok = 0.2, ideal = 0
3. total chlorine (tc = fc + cc): 0 - 10, ok = 1 - 5, ideal = 3
4. ph: 6.2 - 8.4, ok = 7.2 - 7.6, ideal = 7.4
5. calcium hardness (ch): 0 - 1000, ok = 250 - 500, ideal = 375
6. total alkalinity (ta): 0 - 240, ok = 80 - 120, ideal = 100
7. cyanuric acid (cya): 0 - 300, ok = 30 - 100, ideal = 50
8. total bromine (tb): 0 - 20, ok = 2 - 10, ideal = 5
9. temp: 0 - 100

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