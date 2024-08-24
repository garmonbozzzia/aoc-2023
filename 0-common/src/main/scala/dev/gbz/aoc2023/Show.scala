package dev.gbz
package aoc2023

trait Show[-A] { def show(a: A): String }

trait IShow[A] {
  implicit val show: Show[A] = Show.defaultShow
}

object Show {

  implicit class Ops[A: Show](val a: A) {
    def show: String = implicitly[Show[A]].show(a)
    def show[B](b: B)(implicit abShow: Show[(A, B)]): String = abShow.show(a, b)
  }

  implicit def charShow: Show[Char] = defaultShow
  implicit def booleanShow: Show[Boolean] = defaultShow
  implicit def stringShow: Show[String] = defaultShow
  implicit def intShow: Show[Int] = defaultShow
  implicit def longShow: Show[Long] = defaultShow

  implicit def setShow[A: Show]: Show[Set[A]] = set => 
    set.map(implicitly[Show[A]].show).mkString("{", ",", "}")

  implicit def eitherShow[A: Show, B: Show]: Show[Either[A, B]] = {
    case Left(a) => s"Left(${a.show})"
    case Right(b) => s"Right(${b.show})"
  }

  implicit def leftShow[A: Show]: Show[Left[A, Nothing]] = {
    case Left(a) => s"Left(${a.show})"
    // case Right(b) => s"Right(${b.show})"
  }

  implicit def optionShow[A: Show]: Show[Option[A]] = {
    case Some(a) => s"[${a.show}]"
    case None => "[]"
    // case Right(b) => s"Right(${b.show})"
  }
    
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

  // implicit def implicitInt2xArrayTrace(implicit s: Show[Int]): Show[Array[Array[Int]]] = arr => {
  //   val n = arr.flatten.map(s.show(_).size).max
  //   val pre = " "*n
  //   // def f(n: Int) = a.
  //   arr.map(_.map(x =>(pre + s.show(x)).takeRight(n)).mkString("Arr[Int] [", ", ", "]")).mkString("\n","\n","\n")

  // }

  implicit def implicit2xArrayTrace[A](implicit a: Show[A]): Show[Array[Array[A]]] =
    _.map(_.map(a.show).mkString("[", ", ", "]")).mkString("\n","\n","\n")



  implicit def tuple2[A: Show, B: Show]: Show[(A, B)] = _ match {
    case (a, b) => s"(${a.show}, ${b.show})"
  }

  implicit def tuple3[A: Show, B: Show, C: Show]: Show[(A, B, C)] = _ match {
    case (a, b, c) => s"(${a.show}, ${b.show}, ${c.show})"
  }

  implicit def tuple4[A: Show, B: Show, C: Show, D: Show]: Show[(A, B, C, D)] = _ match {
    case (a, b, c, d) => s"(${a.show}, ${b.show}, ${c.show}, ${d.show})"
  }

  def defaultShow[A]: Show[A] = _.toString
}

