import sbt._
import Settings._

object P$layer$$project_name;format="Camel"$ {
  lazy val $project_name;format="camel"$ = 
    project.in(file("$layer$-$project_name;format="lower, hyphen"$"))
      .settings(commonSettings)
  }

// lazy val $project_name;format="camel"$ = P$layer$$project_name;format="Camel"$.$project_name;format="camel"$
