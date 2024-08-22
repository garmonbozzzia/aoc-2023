import sbt._
import Settings._
import P0Common.common

object P1Datatypes {
  def apply() = datatypes

  lazy val datatypes = 
    project.in(file("1-datatypes"))
      .dependsOn(common)
      .settings(commonSettings)
  }

// lazy val datatypes = P1Datatypes()
