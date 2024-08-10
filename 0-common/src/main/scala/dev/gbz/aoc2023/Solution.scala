package dev.gbz
package aoc2023

import zio._
import zio.macros.accessible

@accessible
trait Solution {
  def solve(input: String): IO[Error, String]
  val day: Int
}