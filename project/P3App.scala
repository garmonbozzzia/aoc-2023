import sbt._
import Settings._
import P2Day17.day17

object P3App {
  lazy val app = 
    project.in(file("3-app"))
      .dependsOn(
        P0Common.common,
        P2Day3.day3,
        P2Day5.day5,
        // P2Day6(),
        P2Day19(),
        P2Day16(),
        P2Day10(),
        P2Day21() % cctt,
        day17,
      ).settings(commonSettings)
  }

// lazy val app = P3App.app
