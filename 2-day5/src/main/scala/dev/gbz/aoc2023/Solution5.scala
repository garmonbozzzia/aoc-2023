package dev.gbz
package aoc2023
package day5

import TraceOps._
import zio._
import dev.gbz.aoc2023.Show.defaultShow

case class A2BMap(k1: String, k2: String, mappers: Seq[Mapper])

case object A2BMap {
  // def apply(s: String) = 
  implicit val show: Show[A2BMap] = {
    case _ => "A2BMap"
  }
}

case class Mapper(a: Long, b: Long, l: Long)
object Mapper { implicit val show: Show[Mapper] = defaultShow }

case class MRange(a: Long, l: Long)
object MRange { implicit val show: Show[MRange] = defaultShow }

case class Solve(input: String) {
  // val domain = Domain
  
  val (s"seeds: $seedsStr" :: tail) = input.split("\n\n").toList

  val seeds = seedsStr.split(" ").trace.map(_.toLong).grouped(2).map { case Array(a, l) => MRange(a, l) }.toList.trace

  val mappers = tail.map {
    case s"$k1-to-$k2 map:\n$s" => 
      val seq = s.split("\n").map(s => s.split(" ").map(_.toLong) match {
        case Array(a, b, l) => Mapper(a, b, l)
      }).toSeq
      A2BMap(k1, k2, seq)
  }.toSeq.trace

  val result = ""
  val test = Map(
    "result" -> result
  )
}

case class SolutionLive(day: Int) extends Solution {
  def solve(input: String) = ZIO.succeed(Solve(input).result.toString)
  
  def test(input: String): IO[Error,Map[String,String]] = {
    ZIO.succeed(Solve(input).test)
  }
}

object SolutionLayer {
  def live = ZLayer.succeed(SolutionLive(5))
}
