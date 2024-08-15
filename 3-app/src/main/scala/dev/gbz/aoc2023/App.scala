package dev.gbz
package aoc2023

import zio._
import zio.Console._

object MyApp extends ZIOAppDefault {

  def run = myAppLogic.provide(
    day5.SolutionLayer.live,
    layer.Testing.live,
  )

  val myAppLogic =
    for {
      day    <- Solution.day
      input  <- ZIO.readFile(s"data/$day").map(_.trim).mapError(_ => Error.FileNotFound(s"data/$day"))
      _      <- Testing.testAll
      result <- Solution.solve(input)
      _      <- printLine(result)
    } yield ()
}