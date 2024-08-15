package dev.gbz
package aoc2023

import Helpers._

trait Show[A] { def show(a: A): String }

object Show {

  implicit class Ops[A: Show](val a: A) {
    def show: String = implicitly[Show[A]].show(a)
  }

  implicit def charShow: Show[Char] = defaultShow
  implicit def stringShow: Show[String] = defaultShow
  implicit def intShow: Show[Int] = defaultShow[Int]
  implicit def longShow: Show[Long] = defaultShow[Long]
  implicit def setShow[A: Show]: Show[Set[A]] = set => 
    set.map(implicitly[Show[A]].show).mkString("{", ",", "}")
  
  implicit def implicitStringArrayTrace(implicit a: Show[String]): Show[Array[String]] = 
    _.map(a.show).mkString("[\n", " \n", "\n]")
  
  implicit def implicitArrayTrace[A](implicit a: Show[A]): Show[Array[A]] = 
    _.map(a.show).mkString("[", ", ", "]")

  implicit def implicitSeqTrace[A](implicit a: Show[A]): Show[Seq[A]] = 
    _.map(a.show).mkString("[", ", ", "]")

  implicit def implicitListTrace[A](implicit a: Show[A]): Show[List[A]] = 
    _.map(a.show).mkString("[", ", ", "]")

  implicit def implicitIterableTrace[A](implicit a: Show[A]): Show[Iterable[A]] = 
    _.map(a.show).mkString("[", ", ", "]")
  
  implicit def implicit2xArrayTrace[A](implicit a: Show[A]): Show[Array[Array[A]]] =
    _.map(_.map(a.show).mkString("[", ", ", "]")).mkString("\n","\n","\n")

  implicit def tuple2[A: Show, B: Show]: Show[(A, B)] = _ match {
    case (a, b) => s"(${a.show}, ${b.show})"
  }

  // implicit def setA(implicit a: Show[A]): Show[Set[A]] = _.map(_.)
  def defaultShow[A]: Show[A] = _.toString

}

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