import sbt._
import Settings._

object P2Day5 {
  def apply() = day5

  lazy val day5 = 
    project.in(file("2-days/day5"))
      .dependsOn(P0Common())
      .settings(commonSettings)
}

// lazy val day5 = P2Day5()
