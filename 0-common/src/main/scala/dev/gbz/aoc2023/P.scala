package dev.gbz
package aoc2023

case class P(i: Int, j: Int) {
  def u = P(i - 1, j)
  def d = P(i + 1, j)
  def l = P(i, j - 1)
  def r = P(i, j + 1)

  val pair = (i, j)

  def to(dir: Char) = dir match {
    case 'v' => d
    case '^' => u
    case '>' => r
    case '<' => l
  }
}

object P extends IShow[P] {
  implicit def ordering(O: Ordering[(Int, Int)]): Ordering[P] = (x, y) => O.compare(x.pair, y.pair)
}

