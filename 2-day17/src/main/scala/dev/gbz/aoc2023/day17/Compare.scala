package dev.gbz
package aoc2023
package day17

import TraceOps._
import zio._
import dev.gbz.aoc2023.Helpers._


case class Compare[A](seq1: A, seq2: A)
object Compare {
  implicit def seq[A: Show]: Show[Compare[Seq[A]]] = {
    case Compare(seq1, seq2) => 
      seq1.zip(seq2).map { case (a, b) =>
        if(a == b) s"$a"
        else red(s"${green(a.show)}(${red(b.show)})")
      }.mkString("[", ",", "]")
  }

  implicit def map[A, B]: Show[Compare[Map[A, Seq[Int]]]] = {
    case Compare(map1, map2) => 
      map1.keySet.map { case k =>
        s"$k -> ${Compare(map1(k), map2(k)).show}"
      }.mkString("\n\t", "\n\t", "")
  }
}
