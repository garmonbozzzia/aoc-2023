import sbt._
import Settings._
import P1Datatypes.datatypes

object P2Day10 {
  def apply() = day10

  lazy val day10 = 
    project.in(file("2-days/day10"))
      .dependsOn(P0Common(), datatypes)
      .settings(commonSettings)
}

// lazy val day10 = P2Day10()
