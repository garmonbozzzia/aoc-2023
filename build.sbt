import Settings._
lazy val root = Root.root

lazy val common = P0Common.common
lazy val datatypes = P1Datatypes()
lazy val day3 = P2Day3.day3
lazy val day5 = P2Day5()
lazy val day10 = P2Day10()
lazy val day16 = P2Day16()
lazy val day17 = P2Day17()
lazy val day19 = P2Day19()

lazy val day21 = P2Day21().dependsOn(common)

lazy val aoc2023 = P3App.aoc2023.dependsOn(
  common, day3, day5, day19, day16, day10, day21 % cctt, day17,
)