package dev.gbz
package aoc2023
package day21

import TraceOps._
import zio._
import dev.gbz.aoc2023.Helpers.eval

case class StepTile(s0: Int, seq: Seq[Int])
object StepTile
case class StripeStep(s1: Set[P], s2: Set[P])


case class Solution21(input: String) {

  val STEPS = 26501365

  // val tile = Tile(input, 2)// = ???

  // input.split("\n").map(_.count(_ == '#')).sum
  
  val board = MState(input)
  
  val start = board.withIndices.collect {
    case (i, j, 'S') => P(i, j)
  }.toSet


  def expansion = Tile(input, 1).expansion(start.head.i, start.head.j)

  val innerBorder = board.withIndices.collect { 
    case (i, j, '#') => P(i, j)
  }

  val n = board.n

  // val outer = 
  //   Set.range(0, board.m).flatMap(ij => 
  //     Set(P(0, ij), P(ij, 0), P(ij, n - 1), P(n - 1, ij))
  //   )

  val outerBorder = 
    Set.range(0, board.m).flatMap(ij => 
      Set(P(-1, ij), P(ij, -1), P(ij, n), P(n, ij))
    )

  val border = (innerBorder ++ outerBorder).toSet

  // val corners = Tile.corners(input, 1).map(_.border.map(_.i))

  // a.mapZIO(_ == 1)
    
  // border.show.trace

  // val a = ???

  // Chunk.unfold(StepMap(0, start, Set.empty, Set.empty)) { case stepMap =>
  //   if(stepMap.front.isEmpty) None
  // }

  // def f = eval(StepMap(0, start, Set.empty, Set.empty)) { case stepMap => 
  //   if(stepMap.front.isEmpty) Right(1)
  //   else {
  //     val next = stepMap.next(border)
  //     (next, border, outer).pause
  //     Left(next)
  //   }
  // }

  // Tile()


    val steps = 26501365

    // val res = Seq(
    //   tile.shift1.trace.expansion(0,0).totalDebug(steps, "1"),
    //   tile.shift2.expansion(0,0).totalDebug(steps, "2"),
    //   tile.shift3.expansion(0,0).totalDebug(steps, "3"),
    //   tile.shift4.expansion(0,0).totalDebug(steps, "4"),
    // ).sum - ((steps + 1) / 2) * 4

    def total(steps: Int) = {
      val tile = Tile(input, 1)
      Seq(
        tile.shift1.expansion(0, 0).totalDebug(steps, "1"),
        tile.shift2.expansion(0, 0).totalDebug(steps, "2"),
        tile.shift3.expansion(0, 0).totalDebug(steps, "3"),
        tile.shift4.expansion(0, 0).totalDebug(steps, "4"),
      ).trace.sum - (steps + 1) / 2 * 4
    }


  val result1 = "NOTSOLVED"
  val result2 = "NOTSOLVED"

  val test = Map(
    "result1" -> result1,
    "result2" -> result2,
  )
}
