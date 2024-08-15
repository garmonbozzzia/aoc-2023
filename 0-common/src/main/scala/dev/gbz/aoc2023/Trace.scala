package dev.gbz
package aoc2023

import Helpers._



object TraceOps {

  implicit class PrintOps[A](val a: A) extends AnyVal {
    def pause(implicit S: Show[A]): A = {
      print(S.show(a))
      val b = scala.io.StdIn.readLine()
      if(b == "q") throw new Exception("Exit")
      println(green("-->"))
      a
    }

    // def show: String = 

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
      println(green("-->"))
      a
    }

    def ttrace(tag: String)(implicit S: Show[A]): A = {
      println(s"${yellow(tag)}: ${S.show(a)}")
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

  

  // def defaultShow[A]: Show[A] = _
  // implicit def implicitTrace[A]: Show[A] = _.toString

}