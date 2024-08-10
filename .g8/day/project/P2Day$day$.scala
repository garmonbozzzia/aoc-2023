import sbt._
import Settings._

object P2Day$day$ {
  def apply() = day$day$

  lazy val day$day$ = 
    project.in(file("2-day$day$"))
      .dependsOn(P0Common())
      .settings(commonSettings)
}

// lazy val day$day$ = P2Day$day$()
