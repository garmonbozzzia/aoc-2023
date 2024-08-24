package dev.gbz
package aoc2023
package day21

import TraceOps._
import zio._
import dev.gbz.aoc2023.Helpers.eval


case class Solution21(input: String) {

  input.split("\n").map(_.count(_ == '#')).sum
  
  val board = MState(input)
  val innerBorder = board.withIndices.collect { 
    case (i, j, '#') => P(i, j)
  }

  val n = board.n

  val outer = 
    Set.range(0, board.m).flatMap(ij => 
      Set(P(0, ij), P(ij, 0), P(ij, n - 1), P(n - 1, ij))
    )

  val outerBorder = 
    Set.range(0, board.m).flatMap(ij => 
      Set(P(-1, ij), P(ij, -1), P(ij, n), P(n, ij))
    )

  val border = (innerBorder ++ outerBorder).toSet
    
  import P.setShow
  border.show.trace

  val start = board.withIndices.collect {
    case (i, j, 'S') => P(i, j)
  }.toSet

  // val a = ???

  // Chunk.unfold(StepMap(0, start, Set.empty, Set.empty)) { case stepMap =>
  //   if(stepMap.front.isEmpty) None
  // }

  eval(StepMap(0, start, Set.empty, Set.empty)) { case stepMap => 
    if(stepMap.front.isEmpty) Right(1)
    else {
      val next = stepMap.next(border)
      (next, border, outer).pause
      Left(next)
    }
  }

  val result1 = "NOTSOLVED"
  val result2 = "NOTSOLVED"

  val test = Map(
    "result1" -> result1,
    "result2" -> result2,
  )
}
