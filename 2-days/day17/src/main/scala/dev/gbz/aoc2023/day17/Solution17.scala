package dev.gbz
package aoc2023
package day17

import TraceOps._
import zio._
import dev.gbz.aoc2023.Helpers._

// |
// .-
// |
// точку => направления точка
object const {
  val n = 3
  val maxV = 9999
}
import const._

case class PKey(p: P, d: D, count: Int, sum: Int) {
  // def next(v, d)
}

object PKey extends IShow[PKey] {
  override implicit val show: Show[PKey] = {
    case PKey(P(i, j), D(v), count, sum) => s"($i,$j)${v.toString * count}:$sum"
  }
}

// case class Compare


case class Solution17(input: String) {

  PKey(P(0,0),D.l,0,0).trace

  val board = Board(input.split("\n")).trace

  val state = State(board)
  val state2 = State2(board)

  // board.get(0, 0)
  //   .map(v => D.all.map(PKey(P(0, 0), _, 1, v)))
  //   .foreach(_.foreach(state.update))

  // def next(pkey: PKey, dd: D): Option[PKey] = pkey match { case PKey(p, d, count, sum) => 
  //   val pp = p.to(dd.v)
  //   // if(dd == d && count == 3) None
  //   val res = board.get(pp).flatMap { v => 
  //     val cc = if(dd == d) count else 0
  //     val pk2 = PKey(pp, dd, cc + 1, sum + v)
  //     Option(pk2)
  //       .filter(_.count < n + 1)
  //       .filter(_.d != D.opposite(d))
  //       // .filter(_.sum < state.get(pk2))
  //   }

  //   res
  // }

  def showa(front: Set[PKey]) = {
    // D.all.map(dir => dir.v -> state.state(3)(1)(dir)).trace
    front.ttrace("front")
    // board.allP.map()
    board.allP.map(ps => ps.map { p => 
      val a = state.get(p).isDefined
      // if(p == P(3,1)) a.toString.trace
      val bOpt = front.collectFirst { 
        case PKey(pp, d, count, sum) if p == pp => d.v 
      }
      bOpt.getOrElse(if(a) 'x' else '.')
    }.mkString ).mkString("\n")
    // front.map(_.p)
    
  }
  
  // val b00 = board.get(0,0).get
  val init = Set (
    // PKey(P(0, 0), D.r, 0, b00),
    PKey(P(0, 0), D.r, 0, 0),
    // PKey(P(0 ,0), D.d, 1, b00),
  )

  def evaluated = eval(0, init) { 
    case (_, set) if set.isEmpty => Right(state.last)
    case (i, set) =>
      // (i, set.size).trace
      val next = state.next(set.head)
      state.update(set.head)
      Left(i + 1, (set.tail ++ next))//.pause
  }

  def result1 = evaluated

  // lazy val (result2, path) = state2.res.trace
  // val path = ">>>>>>VVVVV<<<<<<VVVVVV>>>>>>>>>VVVVVVVV>>>>>>>>>>VVVVVVVVV>>>>>>>>^^^^^<<<<<<<<^^^^^>>>>>>>>>^^^^^>>>>>>>>>^^^^^^^^^<<<<^^^^>>>>>>>VVVVV>>>>>>>>VVVV>>>>>>>>>>VVVV>>>>>>>>>>^^^^>>>>>>>^^^^>>>>>>>>VVVV>>>>>>>>>VVVV>>>>>VVVV>>>>>>>>VVVVV>>>>>>VVVV>>>>VVVVVVV>>>>>>>>VVVVVVVVVV>>>>>>>VVVVVVVVV>>>>VVVVVVVV<<<<VVVVVVVV>>>>>VVVVVVVVV<<<<VVVVVVVVVV>>>>>VVVVVVVVVV>>>>VVVVVVVVVV<<<<VVVVVVVVV>>>>VVVVVVV<<<<VVVVVVVVV>>>>VVVVVVVV"
  def total(path: String) = path.foldLeft((0, 0, 0)) {
    case ((i, j, sum), '>') => (i, j + 1, sum + board.get(i, j + 1).get)
    case ((i, j, sum), '<') => (i, j - 1, sum + board.get(i, j - 1).get)
    case ((i, j, sum), 'V') => (i + 1, j, sum + board.get(i + 1, j).get)
    case ((i, j, sum), '^') => (i - 1, j, sum + board.get(i - 1, j).get)
  }
  // total(path).pause

  val board2 = Board2(input.split("\n"))
  board2.result.trace

  def test = Map(
    "result1" -> "",
    "result2" -> board2.result._1,
    // "result1" -> result1,
    // "result2" -> result2._1,
    // "path" -> path
  )
}
