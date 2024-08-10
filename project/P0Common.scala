import sbt._
import Settings._

object P0Common {

  def apply() = common

  lazy val common = 
    project.in(file("0-common"))
      .settings(commonSettings)
  }

// lazy val common = P0Common.common
