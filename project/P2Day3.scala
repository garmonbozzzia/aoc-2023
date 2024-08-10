import sbt._
import Settings._

object P2Day3 {
  lazy val day3 = 
    project.in(file("2-day3"))
      .dependsOn(P0Common.common)
      .settings(commonSettings)
  }

// lazy val day3 = P2Day3.day3
