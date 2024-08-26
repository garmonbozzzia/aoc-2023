package dev.gbz
package aoc2023
package day17

import zio.Chunk

import const._
import Helpers._
import TraceOps._

case class Board2(
  board: MState[Int],
  sumMap: Map[Char, SumState],
  stateV: MState[(Int, Path)],
  stateH: MState[(Int, Path)],
  steps: Seq[Int],
) {

  def length(path: Path) = 
    path.path.map { case (c, n) => c.toString * n }.mkString.foldLeft((0, 0, 0)) {
      case ((i, j, sum), '>') => (i, j + 1, sum + board.get(i, j + 1))
      case ((i, j, sum), '<') => (i, j - 1, sum + board.get(i, j - 1))
      case ((i, j, sum), 'v') => (i + 1, j, sum + board.get(i + 1, j))
      case ((i, j, sum), '^') => (i - 1, j, sum + board.get(i - 1, j))
    }._3


  def f4(
    i: Int, j: Int, dx: Int,
    ii: Int, jj: Int, dir: Char,
    state1: MState[(Int, Path)],
    state2: MState[(Int, Path)],
    // ss: SumState,
    pe: (Int, Int, Path) => PathEnd
  ): Option[PathEnd] = {
    val (y0, path) = state1.get(i, j)
    for {
      dy     <- sumMap(dir).diff(i, j, dx)
      (y, _) <- state2.getOpt(ii, jj)
      yy      = y0 + dy
                if (y > yy)
      newPath = path.add(dir, dx)
      newValue = (y0 + dy, newPath)
    } yield {
      state2.set(ii, jj, newValue)
      (newPath, length(newPath), yy).pauseIf { case (_, a, b) => a != b }
      pe(ii, jj, newPath)
    }
  }

  def f5(
    i: Int, j: Int, dx: Int,
    ii: Int, jj: Int, dir: Char,
    state1: MState[(Int, Path)],
    state2: MState[(Int, Path)],
    // ss: SumState,
    pe: (Int, Int, Path) => PathEnd
  ): Option[PathEnd] = {
    val (y0, path) = state1.get(i, j)
    for {
      dy     <- sumMap(dir).diff(i, j, dx)
      (y, _) <- state2.getOpt(ii, jj)
      yy      = y0 + dy
                if (y > yy)
      newPath = path.add(dir, dx)
      newValue = (y0 + dy, newPath)
    } yield {
      state2.set(ii, jj, newValue)
      (newPath, length(newPath), yy).pauseIf { case (_, a, b) => a != b }
      pe(ii, jj, newPath)
    }
  }

  def result = evalAndPause(
    Set.empty[PathEnd] +
      VPathEnd(0 ,0, Path(Seq.empty)) +
      HPathEnd(0, 0, Path(Seq.empty))
   ) {
    case set if set.isEmpty => 
      Right(
        stateV.last._1.min(stateH.last._1).trace,
        stateV.last.trace,
        stateH.last.trace
      )
    case set => set.head match {
      case VPathEnd(i, j, path) => 
        val down = 
          steps.flatMap { dx => 
            f4(i, j, dx, i + dx, j, 'v',
              stateV, stateH,
              HPathEnd.apply(_, _, _)
            ) 
          }

        val up = 
          steps.flatMap { dx => 
            f4(i, j, dx, i - dx, j, '^',
              stateV, stateH,
              HPathEnd.apply(_, _, _)
            ) 
          }
        
        Left(set.tail ++ down ++ up)

      case HPathEnd(i, j, path) => 
        val right = 
          steps.flatMap { dx => 
            f4(i, j, dx, i, j + dx, '>',
              stateH, stateV,
              VPathEnd.apply(_, _, _)
            ) 
          }

        val left = 
          steps.flatMap { dx => 
            f4(i, j, dx, i, j - dx, '<',
              stateH, stateV,
              VPathEnd.apply(_, _, _)
            ) 
          }
        
        Left(set.tail ++ right ++ left)
    }
      
  }

  // def f(dir: Char) = (i, j, dx) => dir match {
  //   case '>' => sumMap(dir).getOpt(i, j + dx)
  //   case '<' => sumMap(dir).getOpt(i, j - dx)
  //   case '^' => sumMap(dir).getOpt(i - dx, j)
  //   case 'v' => sumMap(dir).getOpt(i + dx, j)
  // }

  // def next(i: Int, j: Int, diff: Int, dir: Char) = 
  //   for {
  //     dx <- sumMap(dir).getOpt(i, j)
  //   }
}

object Board2 {
  def apply(s: Array[String]) = {
    val board = MState(s.map(_.map(_.toString.toInt).toArray).toArray)
    val sumMap = Map(
      '>' -> SumState.right(board),
      '<' -> SumState.left(board),
      'v' -> SumState.down(board),
      '^' -> SumState.up(board),
    )

    val stateV = MState(board.m, board.n, (maxV, Path(Seq.empty)))
    val stateH = MState(board.m, board.n, (maxV, Path(Seq.empty)))

    stateV.set(0, 0, (0, Path(Seq.empty)))
    stateH.set(0, 0, (0, Path(Seq.empty)))

    val steps = Seq(4,5,6,7,8,9,10)

    new Board2(board, sumMap, stateV, stateH, steps)
  }
}