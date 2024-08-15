import sbt._
import Settings._

object P2Day10 {
  def apply() = day10

  lazy val day10 = 
    project.in(file("2-day10"))
      .dependsOn(P0Common())
      .settings(commonSettings)
}

// lazy val day10 = P2Day10()
