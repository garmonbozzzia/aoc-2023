package dev.gbz
package aoc2023
package day17

import TraceOps._
import zio._
import dev.gbz.aoc2023.Helpers._

import const._

case class PState(map: Map[D, Seq[Int]]) {
  def isDefined = min < maxV

  def updatedSeq(seq: Seq[Int], c: Int, v: Int): Seq[Int] = {
    (seq.take(c) ++ seq.drop(c).map(v.min))
  }

  def check(d: D, i: Int, v: Int) = {
    map(d)(i) > v || D.side(d).exists(dd => map(dd)(0) > v)
  }

  // def 

  def update(pk: PKey): PState = pk match { case PKey(P(i, j), d, ii, v) => 
    val updatedMap = 
      D.side(d).foldLeft(map.updated(d, updatedSeq(map(d), ii, v))) { 
        case (map, dd) => map.updated(dd, updatedSeq(map(dd), 0, v))
      }
    // Compare(updatedMap, map).ttrace(pk.show)
    // updatedMap.ttrace(pk.show)
    //
    PState(updatedMap)
  }

  def min = map.values.flatten.min
}
object PState extends IShow[PState] {
  val init = PState(D.all.map(d => d -> Seq.fill(n + 1)(maxV)).toMap)
  // over
}
