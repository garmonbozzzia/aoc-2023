package dev.gbz
package aoc2023
package day6

import TraceOps._
import zio._

case class Solve(input: String) {
  val result = ""
}

case class SolutionLive(day: Int) extends Solution {
  def solve(input: String) = ZIO.succeed(Solve(input).result.toString)
}

object SolutionLayer {
  def live = ZLayer.succeed(SolutionLive(6))
}
