package dev.gbz
package aoc2023
package day21

import TraceOps._
import zio._

case class SolutionLive(day: Int) extends Solution {
  def solve(input: String) = 
    ZIO.succeed(Solution21(input).result1.toString)

  def test(input: String): IO[Error,Map[String,String]] = {
    ZIO.succeed(Solution21(input).test.map { case (k, v) => k -> v.toString })
  }
}

object SolutionLayer {
  def live = ZLayer.succeed(SolutionLive(21))
}
