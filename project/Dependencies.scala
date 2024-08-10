import sbt._
import Keys._

object Dependencies {
  object dev {
    object zio {
      val zio = "dev.zio" %% "zio" % "2.1.7"
      val `zio-stream` = "dev.zio" %% "zio-stream" % "2.1.7"
      val `zio-macros` = "dev.zio" %% "zio-macros" % "2.1.7"
    }
  } 
}