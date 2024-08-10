import sbt._
import Settings._

object P3App {
  lazy val app = 
    project.in(file("3-app"))
      .dependsOn(
        P0Common.common,
        P2Day3.day3,
        // P2Day5.day5,
        P2Day6(),
        P2TestingLive(),
      ).settings(commonSettings)
  }

// lazy val app = P3App.app
