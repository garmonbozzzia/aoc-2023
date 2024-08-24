package dev.gbz
package aoc2023
package day16

import TraceOps._
import zio._
import dev.gbz.aoc2023.Helpers.eval

case class BouncesBoard(arr: Array[String]) {
  def get(i: Int, j: Int): Char = arr.lift(i).flatMap(_.lift(j)).getOrElse('0')
  def get(p: P): Char = get(p.i, p.j)

  val (m, n) = (arr.size, arr.head.size)

  val border = Seq(
    Seq.range(0, arr.size).map(i => (P(i, -1), '>')),
    Seq.range(0, arr.size).map(i => (P(i, n), '<')),
    Seq.range(0, arr.head.size).map(j => (P(-1, j), 'v')),
    Seq.range(0, arr.head.size).map(j => (P( m, j), '^'))
  ).flatten

  def print(margin: Int) = printf(margin, p => get(p.i, p.j))

  def printSet(set: Set[(P, Char)]) = 
    printf(1, p => set.collectFirst { case (pp, c) if pp == p => c }.getOrElse(get(p.i, p.j)))

  def printf(margin: Int, f: P => Char) = {
    Range(-margin, margin + arr.size)
      .map(i => Range(-margin, margin + arr.head.size).map(j => f(P(i, j))).mkString)
      .mkString("\n")
  }
}


case class Solution16(input: String) {

  val board = BouncesBoard(input.split("\n"))
  board.print(1).trace

  def visits(p: P, dir: Char) = eval(Set((p, dir)), Set.empty[(P, Char)]) {
    case (front, visited) =>
      if(front.isEmpty) Right(visited)
      else { 
        val updatedVisited = visited ++ front
        val ffront = front.flatMap { case (p, dir) =>
          val pp = p.to(dir)
          val ss = (dir, board.get(pp)) match {
            case ('>', '|') => "^v"
            case ('<', '|') => "^v"
            case ('v', '-') => "<>"
            case ('^', '-') => "<>"
            case ('>', '/') => "^"
            case ('v', '/') => "<"
            case ('<', '/') => "v"
            case ('^', '/') => ">"
            case ('>', '\\') => "v"
            case ('v', '\\') => ">"
            case ('<', '\\') => "^"
            case ('^', '\\') => "<"
            case (_, '0') => ""
            case (d, _) => s"$d"
          }
          ss.map(pp -> _)
        }
        
        (Left(ffront -- updatedVisited, updatedVisited))
      }
  }

  def energized(p: P, dir: Char) = visits(p, dir).map(_._1).size - 1

  // val result = energized(P(0,-1), '>')
  
  val maxEnergized = board.border.trace.map { case (p, d) => energized(p, d) }.max.trace
  val result = maxEnergized

  val test = Map(
    "result" -> result.toString,
    "solve2" -> maxEnergized
  ).map { case (k, v) => k -> v.toString }
}

case class SolutionLive(day: Int) extends Solution {
  def solve(input: String) = 
    ZIO.succeed(Solution16(input).result.toString)

  def test(input: String): IO[Error,Map[String,String]] = {
    ZIO.succeed(Solution16(input).test)
  }
}

object SolutionLayer {
  def live = ZLayer.succeed(SolutionLive(16))
}
