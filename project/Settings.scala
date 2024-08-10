import sbt._
import Keys._
import Dependencies._

object Settings {
  val commonSettings = Seq(
    scalaVersion := "2.13.14",
    scalacOptions += "-Ymacro-annotations",
    libraryDependencies ++= Seq(
      dev.zio.zio,
      dev.zio.`zio-macros`
    )
    // Здесь можно указать другие общие настройки
  )
}
