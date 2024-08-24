package dev.gbz
package aoc2023
package day17

trait SumState {
  def diff(i: Int, j: Int, dx: Int): Option[Int]
}

object SumState {

  private def sumLine(line: Array[Int]) = 
    line.scanLeft(0)(_ + _).tail.toArray

  def right(board: MState[Int]): SumState = {
    val sum = MState(board.array.map(sumLine))
    (i, j, dx) => sum.getOpt(i, j + dx).map(_ - sum.get(i, j))
  }
  def left(board: MState[Int]): SumState = {
    val sum = MState(board.array.map(x => sumLine(x.reverse).reverse))
    (i, j, dx) => sum.getOpt(i, j - dx).map(_ - sum.get(i, j))
  }
  def down(board: MState[Int]): SumState = {
    val sum = MState(board.array.transpose.map(sumLine).transpose)
    (i, j, dx) => sum.getOpt(i + dx, j).map(_ - sum.get(i, j))
  }
  def up(board: MState[Int]): SumState = {
    val sum = MState(board.array.transpose.map(x => sumLine(x.reverse).reverse).transpose)
    (i, j, dx) => sum.getOpt(i - dx, j).map(_ - sum.get(i, j))
  }
}

