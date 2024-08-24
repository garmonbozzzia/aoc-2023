import sbt._
import Settings._

object P2Day17 {
  def apply() = day17

  lazy val day17 = 
    project.in(file("2-days/day17"))
      .dependsOn(P0Common())
      .settings(commonSettings)
}

// lazy val day17 = P2Day17()
