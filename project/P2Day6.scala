import sbt._
import Settings._

object P2Day6 {
  def apply() = day6

  lazy val day6 = 
    project.in(file("2-days/day6"))
      .dependsOn(P0Common())
      .settings(commonSettings)
}

// lazy val day6 = P2Day6()
