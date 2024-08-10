package dev.gbz
package aoc2023

import zio._
import zio.macros.accessible

@accessible
trait Utils {
  def testFile(filename: String): IO[Error, Unit]
  def excpect(input: String, expected: String): IO[Error, Unit]
  def testAll: IO[Error, Unit]
}