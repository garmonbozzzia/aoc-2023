package dev.gbz
package aoc2023
package day21

import TraceOps._

case class BoardMap(map: Map[Set[P], Char], default: Char) 

// case class BoardMap(map: Map[Set[P], Char], default: Char)

object BoardMap {
  implicit val setShow: Show[BoardMap] = { case BoardMap(map, default) => 
    val is = map.keys.flatten.map(_.i)
    val js = map.keys.flatten.map(_.j)
    val (i0, i1) = (is.min, is.max).trace
    val (j0, j1) = (js.min, js.max).trace
    Seq.tabulate(i1 - i0 + 1, j1 - j0 + 1) { (i, j) => 
      map.collectFirst {
        case (set, c) if set.contains(P(i0 + i, j0 + j)) => c
      }.getOrElse(default)
    }.map(_.mkString).mkString("\n")
  }

  // def apply
}