package dev.gbz
package aoc2023
package day17

import const._ 
import Helpers._
import TraceOps._

case class State2(board: Board) {
  val stateV = MState(board.m, board.n, (maxV, ""))

  // стартовая точка для движения по горизонтали
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
}


