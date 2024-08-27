import sbt._
import Settings._
import P2Day17.day17

object P3App {
  lazy val aoc2023 = 
    project.in(file("3-app"))
      .settings(commonSettings)
  }

// lazy val app = P3App.app
