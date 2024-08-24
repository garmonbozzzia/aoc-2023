import sbt._
import Keys._

object Dependencies {
  object dev {
    object zio {
      val latest = "2.1.7"
      val zio = "dev.zio" %% "zio" % latest
      val `zio-stream` = "dev.zio" %% "zio-stream" % latest
      val `zio-macros` = "dev.zio" %% "zio-macros" % latest
      val `zio-test` = "dev.zio" %% "zio-test" % latest % Test
      val `zio-test-sbt` = "dev.zio" %% "zio-test-sbt" % latest % Test
      val `zio-test-magnolia` = "dev.zio" %% "zio-test-magnolia" % latest % Test
    }
  } 

  object org {
    object scalatest {
      val scalatest = "org.scalatest" %% "scalatest" % "3.2.16" % Test
    }
  }
}

// "org.scalatest" %% "scalatest" % "3.2.16" % Test