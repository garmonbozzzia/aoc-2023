package dev.gbz
package aoc2023
package day5

import TraceOps._
import zio._
import dev.gbz.aoc2023.Helpers.eval

case class A2BMap(k1: String, k2: String, mappers: Seq[Mapper]) {
  def map(r: MRange): Seq[MRange] = {
    mappers.foldLeft(Seq(r)) {
      case (seq, mapper) => 
        seq.flatMap { r =>
          // "".pause
          if(r.v == 0) mapper.map(r)
          else Seq(r)
        }
    }.map(_.normalized)
    // a ++ b
  }
}

case object A2BMap extends IShow[A2BMap] {
  override implicit val show: Show[A2BMap] = {
    case A2BMap(k1, k2, _) => s"A2BMap($k1, $k2)"
  }
}
//          o------o
//        x-o--x   o 
//          o    x-o--x
//        x-o------o-x
//          o x--x o  
//  x--x    o      o  
//          o      o  x--x

case class Mapper(a: Long, b: Long, l: Long) {

  val to = a + l - 1
  val diff = b - a
  // val mapTo
  // val map()

  // def map(r: MRange): Option[MRange] = {
  //   // if(r.from + r.to) 
  //   val aa = a.max(r.from)
  //   val bb = (a + l - 1).min(r.to)
  //   if(r.to < a || to < r.from) None 
  //   else Some(MRange(aa + diff, bb + diff))
  // }

  def map(r: MRange): Seq[MRange] = 
    Seq(
      MRange(r.from, r.to.min(a - 1)),
      MRange(r.from.max(a), r.to.min(to), diff),
      MRange(r.from.max(to + 1), r.to),
    ).filter(_.isDefined)
}

object Mapper extends IShow[Mapper]

case class MRange(a: Long, b: Long, v: Long) {
  val from = a + v
  val to = b + v

  def size = b - a + 1
  def isDefined = from <= to
  def shift(vv: Long) = copy(v = vv + v)
  def normalized = MRange(a + v, b + v, 0)
  def indexOfFirst(that: MRange): Option[Long] = {
    if(to < that.from || that.to < from) None
    else Some((that.from - from).min(0L))
  }
}
object MRange extends IShow[MRange] {
  def apply(from: Long, to: Long) = new MRange(from, to, 0)
}

// object MRange 

case class Solve(input: String) {

  // implicit val show = extends Show[MRange] {def show(a: MRange): String = "MRange"}
  val (s"seeds: $seedsStr" :: tail) = input.split("\n\n").toList

  val seeds = 
    seedsStr.split(" ").ttrace("Seeds")
      .map(_.toLong).grouped(2)
      .map { case Array(a, l) => MRange(a, a + l - 1) }
      .toList.ttrace("Seeds")

  val a2bMaps = tail.map {
    case s"$k1-to-$k2 map:\n$s" => 
      val seq = s.split("\n").map(s => s.split(" ").map(_.toLong) match {
        case Array(a, b, l) => Mapper(a, b, l)
      }).toSeq
      A2BMap(k1, k2, seq)
  }.toSeq.trace

  // val ranges = a2bMaps.last.trace.map(MRange(0, 10000000000L)).trace

  // ranges.foldLeft(0) { case (s, MRange(a, b, _)) =>
    
  // }

  // Range(1, 2).

  val locations = a2bMaps.foldRight(Seq(MRange(0, 10000000000L))) {
    case (a2bMap, ranges) => ranges.flatMap(a2bMap.map)
  }.trace

  val result = eval(0L, locations) { 
    case (res, range +: tail) =>
      seeds.flatMap(_.indexOfFirst(range)).minOption match {
        case Some(v) => Right(res + v)
        case None => Left(res + range.size, tail)
      }
  }


  val test = Map(
    "result" -> result.toString
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
