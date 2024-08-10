import sbt._
import Keys._
import Settings._


object Root {
  lazy val root = (project in file("."))
    .aggregate(
      P0Common.common,
      P2TestingLive(),
      P2Day3.day3,
      P2Day5(),
      P3App.app
    )
    // .aggregate(P0Common.common, module1, module2, aa)
    .settings(
      name := "aoc2023",
      commonSettings
    )
}
