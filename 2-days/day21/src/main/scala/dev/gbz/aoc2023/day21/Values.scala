package dev.gbz.aoc2023.day21

import zio._
import dev.gbz.aoc2023.TraceOps._

case class Values(seq: Chunk[Int], n: Int) {
  val (even, odd) = 
    if(seq.size % 2 == 0) (seq.last, seq.init.last)
    else (seq.init.last, seq.last)

  def total(steps: Long): Long = {
    val corner = Corner.make(steps / n + 1)
    val phase = Phase.make(steps, n)
    val (pa, px) = if(phase.z % 2 == 0) (even, odd) else (odd, even)
    val res = 
      pa * corner.a + 
      px * corner.x +
      corner.z * get(phase.z) +
      corner.y * get(phase.y)

    res//.ttrace("RES")

  }

  def totalDebug(steps: Long, a: String): Long = {
    "".ttrace(a)
    steps.ttrace("steps")
    val corner = Corner.make(steps / n + 1)
    val phase = Phase.make(steps, n)
    // seq.trace
    val (pa, px) = if(phase.z % 2 == 0) (even, odd) else (odd, even)

    (pa, corner.a).ttrace("a")
    (px , corner.x).ttrace("x")
    (phase.y, corner.y, get(phase.y)).ttrace("y")
    (phase.z, corner.z, get(phase.z)).ttrace("z")


    val res = 
      pa * corner.a + 
      px * corner.x +
      corner.z * get(phase.z) +
      corner.y * get(phase.y)

    res//.ttrace("RES")
  }

  def get(i: Long) = {
    seq.lift(i.toInt).getOrElse(if(i % n == 0) odd else even ).toLong
  }

  def total(corner: Corner, phase: Phase): Long = {
    val (pa, px) = 
      if(phase.z % 2 == 0) (even, odd) 
      else (odd, even)

    // (pa, corner.a).ttrace("a")
    // (px , corner.x).ttrace("x")
    // (get(phase.y), corner.y).ttrace("y")
    // (get(phase.z), corner.z).ttrace("z")

    val res = 
      pa * corner.a + 
      px * corner.x +
      corner.z * get(phase.z) +
      corner.y * get(phase.y)

    res//.ttrace("RES")
  }
}
