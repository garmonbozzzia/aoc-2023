import sbt._
import Settings._

object P2TestingLive {

  def apply() = testingLive

  lazy val testingLive = 
    project.in(file("2-testing-live"))
      .dependsOn(P0Common.common)
      .settings(commonSettings)

}

// lazy val testingLive = P2TestingLive()
