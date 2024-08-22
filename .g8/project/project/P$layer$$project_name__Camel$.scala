import sbt._
import Settings._

object P$layer$$project_name;format="Camel"$ {

  def apply() = $project_name;format="camel"$

  lazy val $project_name;format="camel"$ = 
    project.in(file("$layer$-$project_name;format="lower, hyphen"$"))
      .settings(commonSettings)
  }

// Add to build.sbt, project/Root.scala, dependencies. Then sbt reload.
// lazy val $project_name;format="camel"$ = P$layer$$project_name;format="Camel"$()
