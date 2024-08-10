import sbt._
import Settings._

object P1TestingUtils {
  lazy val testingUtils = 
    project.in(file("1-testing-utils"))
      .dependsOn(P0Common.common)
      .settings(commonSettings)
  }

// lazy val testingUtils = P1TestingUtils.testingUtils
