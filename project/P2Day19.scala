import sbt._
import Settings._

object P2Day19 {
  def apply() = day19

  lazy val day19 = 
    project.in(file("2-days/day19"))
      .dependsOn(P0Common())
      .settings(commonSettings)
}

// lazy val day19 = P2Day19()
