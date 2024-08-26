package dev.gbz.aoc2023

import dev.gbz.aoc2023.TraceOps.PrintOps

object Helpers {

  def yellow(s: String) = scala.Console.YELLOW + s + scala.Console.RESET
  def green(s: String) = scala.Console.GREEN + s + scala.Console.RESET
  def red(s: String) = scala.Console.RED + s + scala.Console.RESET


  @scala.annotation.tailrec
  def eval[A, B](a: A)(f: A => Either[A, B]): B = {
    f(a) match {
      case Left(aa) => eval(aa)(f)
      case Right(b) => b
    }
  }

  def evalAndPause[A, B](a: A)(f: A => Either[A, B]): B = 
    eval((1, a)) { case (c, a) =>
      c.ttrace("Eval step")
      f(a) match {
        case Left(v) => 
          "".pause
          Left((c + 1, v))
        case Right(v) => Right(v)
      }
    }
}