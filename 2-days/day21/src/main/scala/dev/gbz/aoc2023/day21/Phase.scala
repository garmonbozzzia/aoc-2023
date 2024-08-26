package dev.gbz.aoc2023.day21

import dev.gbz.aoc2023.IShow

case class Phase(isEven: Boolean, y: Long, z: Long) {
  // val x = if(phase.isEven) even else 
}

// 
object Phase extends IShow[Phase] {
  def make(steps: Long, w: Int) = {
    val pz = steps % w
    Phase(steps % 2 == 0, pz + w, pz)
  }
}
