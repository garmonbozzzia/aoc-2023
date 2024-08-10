package dev.gbz
package aoc2023
package day3

import TraceOps._
import zio._

// case class Field(field: Chunk[Chunk[Int]])

case class Solve(input: String) extends {
  val parsed = input.split("\n")
  // parsed.map(x => )
  val asterix = for {
    i <- parsed.indices
    j <- parsed.head.indices
    if (parsed(i)(j) == '*')
  } yield (i, j)

  val n = parsed.size
  
  asterix.size.trace

  def lineNumbers(i: Int, j: Int) = {
    val r = parsed(i).drop(j + 1).takeWhile(_.isDigit)
    val l = parsed(i).take(j).reverse.takeWhile(_.isDigit).reverse
    if(i < 0 || i <= n) Seq.empty[Int]
    if(parsed(i)(j).isDigit) Seq((l + parsed(i)(j) + r).toInt)
    else Seq(l, r).map(_.toIntOption).flatten
  }

  def numbers(i: Int, j: Int) = 
    Seq(
      lineNumbers(i - 1, j),
      lineNumbers(i, j),
      lineNumbers(i + 1, j),
    ).flatten

  val result = asterix.map { case (i, j) => numbers(i, j) }.collect {
    case Seq(a, b) => a*b
  }.sum
}

case class SolutionLive(day: Int) extends Solution {
  def solve(input: String) = ZIO.succeed(Solve(input).result.toString)
}

object SolutionLayer {
  def live = ZLayer.succeed(SolutionLive(3))
}
