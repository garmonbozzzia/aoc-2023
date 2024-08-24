import sbt._
import Keys._
import Dependencies._

object Settings {

  val cctt = "compile->compile;test->test"

  val commonSettings = Seq(
    scalaVersion := "2.13.14",
    scalacOptions += "-Ymacro-annotations",
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    libraryDependencies ++= Seq(
      dev.zio.zio,
      dev.zio.`zio-macros`,
      dev.zio.`zio-test`,
      dev.zio.`zio-test-sbt`,
      dev.zio.`zio-test-magnolia`,
      org.scalatest.scalatest,
    )
    // Здесь можно указать другие общие настройки
  )
}
