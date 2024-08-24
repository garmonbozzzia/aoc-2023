package dev.gbz
package aoc2023
package day21

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

  val solution = Solution21(input)

  val test1 = test("solution.border.size") {
    assertTrue(
      input.split("\n").map(_.count(_ == '#')).sum ==
      solution.innerBorder.size
    )
  }

  def makeSuite = 
    suite(name) (
      makeTest("result1")(solution.result1),
      makeTest("result2")(solution.result2),
      test1,
      test("square")(assertTrue(solution.board.m == solution.board.n))
    ) @@ TestAspect.timed
}

object SolutionSpec {
  val day = 21
}
