package dev.gbz
package aoc2023
package day17

import TraceOps._
import zio._
import dev.gbz.aoc2023.Helpers._


case class Board(s: Array[String]) {
  val (m,n) = (s.size, s.head.size)
  val (is, js) = (s.indices, s.head.indices)

  val last = P(m - 1, n - 1)
  def get(i: Int, j: Int): Option[Int] = s.lift(i).flatMap(_.lift(j)).map(_.toString.toInt)
  def get(p: P): Option[Int] = get(p.i, p.j)
  def allP = Seq.tabulate(m, n)((i, j) => P(i, j)) //s.indices.flatMap(i => s.head.indices.map(j => P(i, j)))

  def seqSum(seq: Seq[Int]) = seq.scanLeft(0)((a, j) => seq(j) + a)

  // val sumRight = is.map(i => seqSum(s(i))).trace
  // val sumLeft  = is.map(i => js.scanRight(0)((j, a) => get(i, j).map(_ + a).get).init)
  val sumDown  = js.map(j => is.scanLeft(0)((a, i) => get(i, j).map(_ + a).get)).transpose.trace
  // val sumUp    = js.map(j => is.scanRight(0)((i, a) => get(i, j).map(_ + a).get).init).transpose.trace

  //    2  4  1   3   4   3   2   3   1   1   3   2   3
  //    v        vv
  // 0, 2, 6, 7, 10, 14, 17, 19, 22, 23, 24, 27, 29, 32
  // (0, 0,  3) => (4)10 - (1)2
  // (0, 3, -3) => 



  // def nextH(i: Int, j: Int, len: Int) = 
  //   for {
  //     arri <- sumRight.lift(i)
  //     v0   <- arri.lift(j + 1)
  //     v1   <- arri.lift(j + len)
  //     v     = if(v0 > v1) v0 - v1 
  //             else v1 - v0
  //   } yield v

  // def nextLeft(i: Int, j: Int, len: Int) = 
  //   for {
  //     arri <- sumRight.lift(i)
  //     v0   <- arri.lift(j + 1)
  //     v1   <- arri.lift(j + len)
  //     v     = if(v0 > v1) v0 - v1 
  //             else v1 - v0
  //   } yield v

  // def nextV(i: Int, j: Int, len: Int) = 
  //   for {
  //     arri <- sumDown.lift(i)
  //     v0   <- arri.lift(j)
  //     arri <- sumDown.lift(i + len)
  //     v1   <- arri.lift(j)
  //     v     = if(v0 > v1) v0 - v1 
  //             else v1 - v0
  //   } yield v
}

object Board extends IShow[Board] {
  override implicit val show: Show[Board] = 
    b => implicitly[Show[Array[String]]].show(b.s)

  def expected = {
    val board = Board(Array("12", "59"))
    // board.nextV(0, 0, 1) Some

  }
}