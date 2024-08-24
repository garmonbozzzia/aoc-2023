package dev.gbz
package aoc2023
package day17

import TraceOps._
import zio._
import Helpers._

import const._

case class State(board: Board) {

  // val init = D.all.map(d => d -> Seq.fill(n)(maxV)).toMap
  val state: Array[Array[PState]] = Array.fill(board.m,board.n)(PState.init)

  def update(pk: PKey) = pk match { case PKey(P(i, j), d, ii, v) => 
    state(i)(j) = state(i)(j).update(pk)
  }

  def next(pkey: PKey): Seq[PKey] = {
    val a = get(pkey.p)
    if(!get(pkey.p).check(pkey.d, pkey.count, pkey.sum)) Seq.empty
    else D.all.flatMap(next(pkey, _)).toSeq
  }

  def next(pkey: PKey, dd: D): Option[PKey] = pkey match { case PKey(p, d, count, sum) => 
    val pp = p.to(dd.v)
    val cc = if(dd == d) count else 0
    // if(dd == d && count == 3) None
    val res = board.get(pp).flatMap { v => 
      Option(PKey(pp, dd, cc + 1, sum + v))
        .filter(_.count < n + 1)
        .filter(_.d != D.opposite(d))
    }
    res
  }


  def get(pKey: PKey) = state(pKey.p.i)(pKey.p.j).map(pKey.d)(pKey.count)
  def get(p: P) = state(p.i)(p.j)
  def last = state.last.last.min
}
