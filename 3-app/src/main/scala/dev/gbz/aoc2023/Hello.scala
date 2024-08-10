package dev.gbz
package aoc2023

import zio._
import zio.Console._

object MyApp extends ZIOAppDefault {

  def run = myAppLogic.provide(
    day6.SolutionLayer.live,
    layer.Utils.live,
  )

  val myAppLogic =
    for {
      // _      <- Utils.test("data/3.a")
      day    <- Solution.day
      input  <- ZIO.readFile(s"data/$day")
      _      <- Utils.testAll
      result <- Solution.solve(input)
      _      <- printLine(result)
    } yield ()
}