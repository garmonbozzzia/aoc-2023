package dev.gbz
package aoc2023
package day21

import TraceOps._

case class BoardMap(default: Char, sets: (Set[P], Char)*) 

// case class BoardMap(map: Map[Set[P], Char], default: Char)

object BoardMap {

  // def app

  implicit val setShow: Show[BoardMap] = { case BoardMap(default, sets @ _*) => 
    val is = sets.flatMap(_._1.map(_.i))
    val js = sets.flatMap(_._1.map(_.j))
    val (i0, i1) = (is.min, is.max)
    val (j0, j1) = (js.min, js.max)
    Seq.tabulate(i1 - i0 + 1, j1 - j0 + 1) { (i, j) => 
      sets.collectFirst {
        case (set, c) if set.contains(P(i0 + i, j0 + j)) => c
      }.getOrElse(default)
    }.map(_.mkString).mkString("\n")
  }

  // def apply
}