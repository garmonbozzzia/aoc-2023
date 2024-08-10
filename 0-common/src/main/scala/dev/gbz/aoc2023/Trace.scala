package dev.gbz
package aoc2023

object TraceOps {

  implicit class PrintOps[A](val a: A) extends AnyVal {
    def trace(implicit S: Show[A]): A = {
      println(S.show(a))
      a
    }

    def traceOk(implicit S: Show[A]): A = {
      println(Console.GREEN + S.show(a) + Console.RESET)
      a
    }

    def traceError(implicit S: Show[A]): A = {
      println(Console.RED + S.show(a) + Console.RESET)
      a
    }

    def tap[B](f: A => B): A = { f(a); a }

    def pause = {
      print(a)
      val b = scala.io.StdIn.readLine()
      if(b == "q") throw new Exception("Exit")
      a
    }

    def ttrace(tag: String)(implicit S: Show[A]): A = {
      println(s"$tag: ${S.show(a)}")
      a
    }

    def ftrace[B](f: A => B): A = {
      println(f(a))
      a
    }

    def iftrace[B](p: A => Boolean, f: A => B): A = {
      if(p(a)) println(f(a))
      a
    }
  }

  trait Show[A] { def show(a: A): String }
  implicit def implicitStringArrayTrace[A](implicit a: Show[String]): Show[Array[String]] = _.map(a.show).mkString("[\n", " \n", "\n]")
  implicit def implicitArrayTrace[A](implicit a: Show[A]): Show[Array[A]] = _.map(a.show).mkString("[", ", ", "]")
  implicit def implicit2xArrayTrace[A](implicit a: Show[A]): Show[Array[Array[A]]] =
    _.map(_.map(a.show).mkString("[", ", ", "]")).mkString("\n","\n","\n")
  implicit def implicitTrace[A]: Show[A] = _.toString

}