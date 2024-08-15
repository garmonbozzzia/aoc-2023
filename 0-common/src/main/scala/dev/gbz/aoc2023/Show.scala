package dev.gbz
package aoc2023

trait Show[-A] { def show(a: A): String }

trait IShow[A] {
  implicit val show: Show[A] = Show.defaultShow
}

object Show {

  implicit class Ops[A: Show](val a: A) {
    def show: String = implicitly[Show[A]].show(a)
  }

  implicit def charShow: Show[Char] = defaultShow
  implicit def stringShow: Show[String] = defaultShow
  implicit def intShow: Show[Int] = defaultShow
  implicit def longShow: Show[Long] = defaultShow

  implicit def setShow[A: Show]: Show[Set[A]] = set => 
    set.map(implicitly[Show[A]].show).mkString("{", ",", "}")
  
  implicit def implicitStringArrayTrace[A](implicit S: Show[A]): Show[Iterable[A]] = arr => {
    val as = arr.map(S.show)
    val b = as.mkString("[", ", ", "]")
    if(b.size < 70) b
    else as.mkString("[\n  ", "\n  ", "\n]")
  }

  implicit def implicitIterableTrace[A](implicit S: Show[A]): Show[Array[A]] = arr => {
    val as = arr.map(S.show)
    val b = as.mkString("[", ", ", "]")
    if(b.size < 70) b
    else as.mkString("[\n  ", "\n  ", "\n]")
  }
  
  implicit def implicit2xArrayTrace[A](implicit a: Show[A]): Show[Array[Array[A]]] =
    _.map(_.map(a.show).mkString("[", ", ", "]")).mkString("\n","\n","\n")

  implicit def tuple2[A: Show, B: Show]: Show[(A, B)] = _ match {
    case (a, b) => s"(${a.show}, ${b.show})"
  }

  def defaultShow[A]: Show[A] = _.toString
}

