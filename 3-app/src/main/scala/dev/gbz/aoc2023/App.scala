package dev.gbz
package aoc2023

import zio._
import zio.Console._

object MyApp1 extends ZIOAppDefault {

  def run = myAppLogic.provide(
    day10.SolutionLayer.live,
    // layer.Testing.live,
  )

  val myAppLogic =
    for {
      day    <- Solution.day
      // _      <- Testing.testAll
      input  <- ZIO.readFile(s"data/$day")
                  .map(_.trim)
                  .mapError(_ => Error.FileNotFound(s"data/$day"))
      result <- Solution.solve(input)
      _      <- printLine(result)
    } yield ()
}

// object MyApp2 extends ZIOAppDefault {

//   def run = myAppLogic.provide(
//     day10.SolutionLayer.live,
//     layer.Testing.live,
//   )

//   val myAppLogic =
//     for {
//       day    <- Solution.day
//       input  <- ZIO.readFile(s"data/$day")
//                   .map(_.trim)
//                   .mapError(_ => Error.FileNotFound(s"data/$day"))
//       _      <- Testing.testAll
//       result <- Solution.solve(input)
//       _      <- printLine(result)
//     } yield ()
// }
