package dev.gbz.aoc2023.day21

import zio._
import dev.gbz.aoc2023.Helpers.eval
import dev.gbz.aoc2023.IShow

case class Corner(a: Long, x: Long, z: Long) {
  val y = z - 1
  def inc = {
    val da = (z + 1) % 2 
    val k = (z) / 2
    Corner(a + k - da, x + k, z + 1)
  }
}

case object Corner extends IShow[Corner] {

  def withSteps(steps: Int, w: Int) = 
    make((steps / w) + 1)

  def make(n: Long) = {
    val p = (n - 1).max(0) / 2L
    val pp = p * p
    val q = (n - 2) / 2L
    val qq = q * (q + 1)
    if(n % 2 == 1) Corner(qq, pp, n) 
    else Corner(pp, qq, n)
  }

  def makeInc(n: Int) = {
    if(n == 1) Corner(0, 0, 1)
    else if(n == 2) Corner(0, 0, 2)
    else eval(Corner(0, 0, 2)) {
      case corner if corner.z == n => Right(corner)
      case corner => Left(corner.inc)
    }
  }
}

// 3 -> 0 4 -> 2 5 -> 2 6 -> 4 

  //     .................
  //     .................
  //     .................
  //     z................
  //     yz...............
  //     xyz..............
  //     axyz.............
  //     xaxyz............
  //     axaxyz...........
  //     xaxaxyz..........
  //     axaxaxyz.........
  //     xaxaxaxyz........
  //     axaxaxaxyz.......
  //     xaxaxaxaxyz......
  //     axaxaxaxaxyz.....
  // 
  // z + zy, yz,  
  // (a, b) => (a + b, b + x) + 
  // (sum, d) => (sum + )
  //  zyz + zyz + x2y2 
  // 2x*2a
  // z -> n
  // y -> n - 1
  // (n even) a -> (n/2 - 1)^2 
  //          x -> (n/2 - 1)^2 
  //
  // x -> n - 1
  // n -> n/2 + 2x2a*b
  // 
  // x = 4, a = 2, z = 5, y = 4
  // y = z - 1
  // x = (y - 1)/2
  // z = n * 2 + 1
  // y = 2 * n
  // n = 2
  // x = n * n
  // 
