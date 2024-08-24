package dev.gbz
package aoc2023
package day$day$

import zio.test.{test, _}
import zio._

case class Board(s: String)

case class SolutionSpec(
  name: String, 
  input: String,
  expected: Map[String, String],
) {

  def makeTest[A](name: String)(a: => A) = {
    test(name) {
      assertTrue(a.toString == expected(name))
    }
  }

  val solution = Solution$day$(input)

  def makeSuite = 
    suite(name) (
      makeTest("result1")(solution.result1),
      makeTest("result2")(solution.result2),
    ) @@ TestAspect.timed
}

object SolutionSpec {
  val day = $day$
}
