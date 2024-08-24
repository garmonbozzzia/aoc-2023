package dev.gbz
package aoc2023
package day$day$

import TraceOps._
import zio._

case class SolutionLive(day: Int) extends Solution {
  def solve(input: String) = 
    ZIO.succeed(Solution$day$(input).result.toString)

  def test(input: String): IO[Error,Map[String,String]] = {
    ZIO.succeed(Solution$day$(input).test.map { case (k, v) => k -> v.toString })
  }
}

object SolutionLayer {
  def live = ZLayer.succeed(SolutionLive($day$))
}
