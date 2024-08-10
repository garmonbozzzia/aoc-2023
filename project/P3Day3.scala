import sbt._
import Settings._

object P3Day3 {
  lazy val day3 = 
    project.in(file("3-day3"))
      .dependsOn(P0Common.common)
      .settings(commonSettings)
  }

// lazy val day3 = P3Day3.day3
