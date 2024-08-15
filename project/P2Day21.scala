import sbt._
import Settings._

object P2Day21 {
  def apply() = day21

  lazy val day21 = 
    project.in(file("2-day21"))
      .dependsOn(P0Common())
      .settings(commonSettings)
}

// lazy val day21 = P2Day21()
