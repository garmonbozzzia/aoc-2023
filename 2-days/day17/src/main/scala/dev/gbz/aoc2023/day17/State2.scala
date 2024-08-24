package dev.gbz
package aoc2023
package day17

import const._ 
import Helpers._
import TraceOps._

// case c

// case class Or
// case class State3(v: Int, path: String) {
  
// }

case class State2(board: Board) {
  // val stateV = Array.fill(board.m, board.n)((maxV, ""))
  val stateV = MState(board.m, board.n, (maxV, ""))

  // стартовая точка для движения по горизонтали
  // val stateH = Array.fill(board.m, board.n)((maxV, ""))
  val stateH = MState(board.m, board.n, (maxV, ""))

  val steps = Seq(4, 5, 6, 7, 8, 9, 10)

  stateH.set(0, 0, (0, ""))
  stateV.set(0, 0, (0, ""))

  def pathLen(path: String) = path.foldLeft((0, 0, 0)) {
    case ((i, j, sum), '>') => (i, j + 1, sum + board.get(i, j + 1).get)
    case ((i, j, sum), '<') => (i, j - 1, sum + board.get(i, j - 1).get)
    case ((i, j, sum), 'V') => (i + 1, j, sum + board.get(i + 1, j).get)
    case ((i, j, sum), '^') => (i - 1, j, sum + board.get(i - 1, j).get)
  }._3

  // def moveDown(i: Int, j: Int, ii: Int) = {
  //   for {
  //     (a, path) <- stateV.getOpt(i, j)
  //     v <- board.nextDown(i, j, ii)
  //     (h, _) <- getH(i + ii, j)
  //     b = a + v
  //     _ = ((i, j, ii).ttrace("(i, j, ii)"))
  //     _ = ((a, v, h, b).ttrace("(a,v,h,b)"))
  //     if(h > b) 
  //     c = if(ii > 0) "V" * ii else "^"
  //     updatedPath = path + (if(ii > 0) "V" * ii else "^" * (- ii))
  //   } yield {
  //     stateH(i + ii)(j) = (b, updatedPath)
  //     (updatedPath, b, pathLen(updatedPath)).pause//("pathLen")
  //     // "".pa
  //     // s"${green(b.show)}(${red(h.show)})".ttrace(">" + (i + ii, j).show)
  //     (i + ii, j, '>')
  //   }

  // }

  // def moveUp(i: Int, j: Int, len: Int) = ???
  // def moveLeft(i: Int, j: Int, len: Int) = ???
  // def moveRight(i: Int, j: Int, len: Int) = ???

  // def evalf = eval (Set((0, 0, "V"), (0, 0, "H"))) { case (moves) =>
  //   if(moves.isEmpty) {
  //     val (res1@(a, s1), res2@(b, s2)) = (stateH.last, stateV.last)
  //     (if (a < b) s1 else s2).trace
  //     Right(if (a < b) res1 else res2)
  //   } else moves.head match { 
  //     case (i, j, "V") =>
  //       val (a, path) = stateV.get(i, j)
  //       val movesDown = steps.map { moveDown(i, j, _) }
  //       val movesUp = steps.map { moveUp(i, j, _) }
  //       Left(moves.tail ++ movesDown ++ movesUp)
  //     case (i, j, "H") =>
  //       val movesLeft = steps.map { moveLeft(i, j, _) }
  //       val movesRight = steps.map { moveRight(i, j, _) }
  //       Left(moves.tail ++ movesLeft ++ movesRight)
  //   }

  
  // }


  // def res = eval (0, Set((0, 0, '>'), (0, 0, 'V'))) { case (count, set) =>
  //   (count, set.size).trace
  //   if(set.isEmpty) {
  //     val (res1@(a, s1), res2@(b, s2)) = (stateH.last, stateV.last)
  //     (if (a < b) s1 else s2).trace
  //     Right(if (a < b) res1 else res2)
  //   } else set.head match { 
  //     case (i, j, 'V') =>
  //       val moves = steps.flatMap { ii => 
  //         for {
  //           (a, path) <- stateV.get(i, j)
  //           iii = if (ii > 0) ii + 1 else ii
  //           v <- board.nextV(i, j, iii)
  //           (h, _) <- getH(i + ii, j)
  //           b = a + v
  //           _ = ((i, j, ii).ttrace("(i, j, ii)"))
  //           _ = ((a, v, h, b).ttrace("(a,v,h,b)"))
  //           if(h > b) 
  //           c = if(ii > 0) "V" * ii else "^"
  //           updatedPath = path + (if(ii > 0) "V" * ii else "^" * (- ii))
  //         } yield {
  //           stateH(i + ii)(j) = (b, updatedPath)
  //           (updatedPath, b, pathLen(updatedPath)).pause//("pathLen")
  //           // "".pa
  //           // s"${green(b.show)}(${red(h.show)})".ttrace(">" + (i + ii, j).show)
  //           (i + ii, j, '>')
  //         }
  //       }
  //       Left(count + 1, set.tail ++ moves)//.pause

  // // 1>>>>5
  // // 1    v
  // // 1    v
  // // 1    9

  //     case (i, j, '>') =>
  //       val moves = steps.flatMap { jj => 
  //         for {
  //           (a, path) <- getH(i, j)
  //           h <- board.nextH(i, j, jj)
  //           (v, _) <- getV(i, j + jj)

  //           b = a + h
  //           _ = ((i, j, jj).ttrace("(i, j, jj)"))
  //           _ = ((a, h, v, b).ttrace("a,h,v,b"))
  //           if(v > b) 
  //           c = if(jj > 0) ">" else "<"
  //           updatedPath = path + (if(jj > 0) ">" * jj else "<" * (-jj))

  //         } yield {
  //           stateV(i)(j + jj) = (b, updatedPath)
  //           (updatedPath, b, pathLen(updatedPath)).pause//.ttrace("pathLen")

  //           // s"${green(b.show)}(${red(v.show)})".ttrace("V" + (i, j + jj).show)
  //           (i, j + jj, 'V')
  //         }
  //       }
  //       Left(count + 1, set.tail ++ moves)//.pause
  //   }
  // }
}


