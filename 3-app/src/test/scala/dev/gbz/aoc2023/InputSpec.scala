package dev.gbz
package aoc2023

import zio._

import zio.test.{test, _}
import zio.test.Assertion._
import dev.gbz.aoc2023.TraceOps.PrintOps
// import dev.gbz.aoc2023.day17.Board.expected

import day21._

case class Expectation(name: String, input: String, expected: Map[String, String]) {
  def makeSuite = SolutionSpec(name, input, expected).makeSuite
}


object InputSpec extends ZIOSpecDefault  {

  val day = SolutionSpec.day

  val sep1 = "\n------------\n"
  val sep2 = "\n============\n"

  def parse(i: Int, s: String): Chunk[Expectation] = 
    Chunk.fromArray(s.split(sep2))
      .zipWithIndex
      .map { case (s, j) =>
        s.split(sep1) match { 
          case Array(input, data) =>
            val expectedMap = data.split("\n").map {
              case s"$key=$value" => (key.trim, value.trim)
              case s"- $key:$value" => (key.trim, value.trim)
              case result         => ("result", result)
            }.toMap
            Expectation(s"case $day.$i.$j", input, expectedMap)
        }
      }

  def spec = suite(s"aoc2023.day$day") {
    val a = Chunk.range(0, 10)
      .map(i => (i, s"data/${day}.$i"))
      .mapZIO { case (i, filename) =>
        ZIO.readFile(filename)
          .map(parse(i, _))
          .orElseSucceed(Chunk.empty)
      }
      .map(_.flatten)
      .map { 
        _.map { _.makeSuite }
      }
    a
  }
}