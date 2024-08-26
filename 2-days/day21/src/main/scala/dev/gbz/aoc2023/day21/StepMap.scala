package dev.gbz
package aoc2023
package day21

import TraceOps._

case class StepMap(steps: Int, front: Set[P], s1: Set[P], s2: Set[P]) {

  def total = front.size + s2.size

  def next(border: Set[P]): StepMap = {
    if(front.isEmpty) StepMap(steps + 1, front, s2, s1)
    else {
      val updatedFront = front.expanded4.filterNot(border).filterNot(s1)
      // this.trace
      StepMap(steps + 1, updatedFront, s2 ++ front, s1)//.trace
    }
  }
}

object StepMap {

  import Show.setShow
  implicit val show: Show[StepMap] = {
    case sm => 
      s"Step: ${sm.steps}\nTotal: ${sm.total}"
  }
  // implicit val show: Show[StepMap] = x => Seq(x.front.show, x.s1.show, x.s2.show).show

  implicit val showC: Show[(StepMap, Set[P], Set[P])] = { case (sm, border, outer) =>

    sm.show + "\n" + BoardMap(
      '.',
      sm.s1 -> ' ',
      sm.front -> 'O',
      sm.s2 -> ' ',
      border -> ' ',
    ).show
  }

  implicit val showB: Show[(StepMap, Set[P])] = { case (sm, border) =>

    sm.show + "\n" + 
    BoardMap(
      ' ',
      sm.s1 -> '-',
      sm.front -> 'O',
      sm.s2 -> 'o',
      border -> '#',
    ).show
  }
}


