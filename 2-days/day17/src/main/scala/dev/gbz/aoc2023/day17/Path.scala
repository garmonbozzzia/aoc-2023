package dev.gbz
package aoc2023
package day17

import TraceOps._

case class Path(path: Seq[(Char, Int)]) {
  def add(dir: Char, len: Int) = Path(path.appended((dir, len)))
}

object Path extends IShow[Path] {
  override implicit val show: Show[Path] = 
    _.path.map { case (c, n) => s"$n$c" }.mkString
  
}

sealed trait PathEnd
object PathEnd extends IShow[PathEnd] {
  override implicit val show: Show[PathEnd] = {
    case VPathEnd(i, j, path) => path.show 
    case HPathEnd(i, j, path) => path.show  
  }
}

final case class VPathEnd(i: Int, j: Int, path: Path) extends PathEnd
final case class HPathEnd(i: Int, j: Int, path: Path) extends PathEnd
