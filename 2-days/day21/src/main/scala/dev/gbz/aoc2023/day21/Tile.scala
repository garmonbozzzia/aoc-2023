package dev.gbz
package aoc2023
package day21

import TraceOps._
import dev.gbz.aoc2023.Helpers.eval
import zio._
import dev.gbz.aoc2023.Helpers.evalAndPause

case class Tile(innerBorder: Set[P], n: Int) {

  val outerBorder = 
    Set.range(0, n).flatMap(ij => 
      Set(P(-1, ij), P(ij, -1), P(ij, n), P(n, ij))
    )

  val border = innerBorder ++ outerBorder

  def shiftSet(set: Set[P]) = set.map { p => P((p.i + n / 2 + 1) % n, (p.j + n / 2 + 1) % n) }

  def mirror = (i: Int) => n - i - 1

  def ff(fi: Int => Int, fj: Int => Int) = 
    Tile(shiftSet(innerBorder.map { case P(i, j) => P(fi(i), fj(j)) }), n)

  def shift1 = ff(identity, identity)
  def shift2 = ff(mirror, identity)
  def shift3 = ff(identity, mirror)
  def shift4 = ff(mirror, mirror)

  def expansion(i: Int, j: Int) = 
    eval(
      StepMap(0, Set(P(i, j)), Set.empty, Set.empty), Chunk(0), 0
    ) { case (stepMap, res, i) => 
      if(stepMap.front.isEmpty) Right(Values(res.tail, n))
      else {
        val next = stepMap.next(border)
        // if(i < 5) (next, border).ttrace("AAAAAAAAA")
        Left(next, res.appended(stepMap.total), i + 1)
      }
    }
}

object Tile extends IShow[Tile] {

  override implicit val show: Show[Tile] = t => 
    BoardMap(' ', t.border -> '#').show

  def corners(s: String, mult: Int) = {
    val a1 = s.linesIterator.toList
    val a2 = a1.reverse
    val a3 = a1.transpose.map(_.mkString)
    val a4 = a3.reverse

    Chunk(a1,a2,a3,a4).map(_.mkString("\n")).map(apply(_, mult))
  }

  def apply(input: String, mult: Int) = {
    val arr = input.split("\n").map(_ * mult)
    val board = MState(Seq.fill(mult)(arr).flatten.toArray)
    val n = board.n
    
    val innerBorder = 
      board.withIndices.collect { 
        case (i, j, '#') => P(i, j)
      }.toSet

    // val outerBorder = 
    //   Set.range(0, board.m).flatMap(ij => 
    //     Set(P(-1, ij), P(ij, -1), P(ij, n), P(n, ij))
    //   )

    // val border = innerBorder ++ outerBorder
    
    // import P.setShow
    // border.show.trace
    
    new Tile(innerBorder, n)
  }
}