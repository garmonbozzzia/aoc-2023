package dev.gbz
package aoc2023
package day10

import TraceOps._
import zio._
import dev.gbz.aoc2023.Helpers.eval
import dev.gbz.aoc2023.Show.defaultShow

object DirOps {
  private val inout1 = Seq (
    'L' -> ('v', '>'),
    'J' -> ('v', '<'),
    '7' -> ('^', '<'),
    'F' -> ('^', '>'),
    '|' -> ('v', 'v'),
    '-' -> ('>', '>'),
  )

  private val opp = Map (
    'v' -> '^',
    '^' -> 'v',
    '>' -> '<',
    '<' -> '>',
  )

  private val inout = inout1.flatMap {
    case (k, (in1, out1)) => 
      Seq(
        (in1, k) -> out1,
        (opp(out1), k) -> opp(in1),
      )
  }.toMap

  def apply(dir: Char, point: Char) = inout((dir, point))

}

case class Board(board: Array[String]) {
  def apply(p: P) = board.lift(p.i).flatMap(_.lift(p.j)).getOrElse('.')
  val (m, n) = (board.size, board.head.size)
  val all = 
    board.indices.flatMap(i => 
    board.head.indices.map(j => P(i, j))).toSet

  val startP = board.indices.collectFirst {
    case i if board(i).indexWhere(_ == 'S') != -1 => 
      P(i, board(i).indexWhere(_ == 'S'))
  }.get

  val startDir = 
    if( "|F7".contains(apply(startP.u))) '^'
    else if( "|JL".contains(apply(startP.d))) 'v'
    else '>'

  (startP, startDir).trace
  // def nextDir(p: P, dir: Char) = {
  //   DirOps.inout(dir, apply(p.to(dir)))
  // }
}

case class Solution10(input: String) {
  val board = Board(input.split("\n"))
  val (m, n) = (board.m, board.n)
  
  object PointNeighborhood {
    val ul = P(-1, -1)
    val ur = P(-1,  1)
    val dl = P( 1, -1)
    val dr = P( 1,  1)
    val c  = P( 0,  0)
    val u  = P(-1,  0)
    val d  = P( 1,  0)
    val l  = P( 0, -1)
    val r  = P( 0,  1)
  }

  val pattern = {
    import PointNeighborhood._
    Map(
      '.' -> Set.empty[P],
      '|' -> Set(c, u, d),
      '-' -> Set(c, l, r),
      'L' -> Set(c, u, r),
      'J' -> Set(c, u, l),
      '7' -> Set(c, d, l),
      'F' -> Set(c, d, r),
      'S' -> Set(c)
    )
  }

  val outerBorder = Set(
    Set.range(-2, 2 * n + 1).map(j => P(-2, j)),
    Set.range(-2, 2 * n + 1).map(j => P(2 * m, j)),
    Set.range(-2, 2 * m + 1).map(i => P(i, -2)),
    Set.range(-2, 2 * m + 1).map(i => P(i, 2 * n)),
  ).flatten

  val point = board.startP.to(board.startDir) 
  def borderPoints1 = eval(board.startDir, board.startP.to(board.startDir), Seq.empty[P]) { case (dir, p, seq) =>
    if(board(p) == 'S') Right(seq.appended(p))
    else {
      val newDir = DirOps(dir, board(p))
      Left(newDir, p.to(newDir), seq.appended(p))
    }
  }
  borderPoints1.toList.map(board(_)).mkString.ttrace("border")

  // val borderPoints = board.all
  val borderPoints = borderPoints1.toSet
  
  val border = 
    borderPoints.flatMap { case p@P(i, j) =>
      pattern(board(p)).map { 
        case P(ii,jj) => P(2 * i + ii, 2 * j + jj) 
      }
    } ++ outerBorder

  val inner = eval(Set(
    Set.range(-1, 2 * n).map(j => P(-1, j)),
    Set.range(-1, 2 * n).map(j => P(2 * m - 1, j)),
    Set.range(-1, 2 * m).map(i => P(i, -1)),
    Set.range(-1, 2 * m).map(i => P(i, 2 * n - 1)),
  ).flatten, Set.empty[P]) { case (bs, inner) => 
      if(bs.isEmpty) Right(inner)
      else {
        val updated = inner ++ bs
        val expanded = bs.expanded9 diff updated diff border 
        Left(expanded, updated)
      }    
  }

  def show(codes: String, xa: Int, xb: Int, ya: Int, yb: Int) = 
    Seq.range(xa, xb).flatMap { i => 
      Seq.range(ya, yb).map { j => 
        if(border(P(i, j))) 0
        else if(inner(P(i,j))) 1
        else if(i % 2 == 0 && j % 2 == 0) 2
        else 3
      }.map(codes)
    }.mkString.grouped(yb - ya).mkString("\n")

  show("x.A.", -2, 2 * m + 1, -2, 2 * n + 1).trace
  show("xoA.", -2, 2 * m + 1, -2, 2 * n + 1).count(_ == 'A').trace

  val result = show("x.A.",-2, 2 * m + 1, -2, 2 * n + 1).count(_ == 'A')
}

case class SolutionLive(day: Int) extends Solution {
  def solve(input: String) = ZIO.succeed(Solution10(input).result.toString)
  
  def test(input: String): IO[Error,Map[String,String]] = 
    ZIO.succeed(
      Map(
        "result2" -> Solution10(input).result.toString,
        "result1" -> "NA",
        // "result" -> Solution10(input).result.toString,
      )
    )
}

object SolutionLayer {
  def live = ZLayer.succeed(SolutionLive(10))
}
