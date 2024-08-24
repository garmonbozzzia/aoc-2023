import sbt._
import Settings._

object P2Day16 {
  def apply() = day16

  lazy val day16 = 
    project.in(file("2-days/day16"))
      .dependsOn(P0Common(), P1Datatypes())
      .settings(commonSettings)
}

// lazy val day16 = P2Day16()
