package dev.gbz
package aoc2023

import zio._
import zio.macros.accessible

@accessible
trait Testing {
  def testFile(filename: String): IO[Error, Unit]
  def expect(input: String, expectedMap: Map[String, String]): IO[Error, Unit]
  def testAll: IO[Error, Unit]
}