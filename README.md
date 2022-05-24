Quill
-----
>Quill feature tests and performance benchmark.

Test
----
1. sbt clean test

Benchmark
---------
>See Performance class for details.
1. sbt jmh:run
>**Warning** Using JDK17, throws: java.lang.ClassNotFoundException: java.sql.ResultSet

Results
-------
>OpenJDK Runtime Environment (Zulu 8.56.0.23-CA-macos-aarch64) (build 1.8.0_302-b08)
1. addTodo - 21.284
2. listTodos - 3.375
3. updateTodo - 6.536
>Total time: 607 s (10:07), 10 warmups, 10 iterations, in microseconds, completed 2021.11.5