package dev.gbz
package aoc2023

import zio._
import TraceOps._

case class UtilsLive(solution: Solution) extends Utils {
  def excpect(input: String, answer: String) = 
    solution.solve(input).flatMap { result =>
      if(result == answer) Console.printLine("OK").orDie
      else Console.printLine(s"$result != $answer").orDie
    }

  val s1 = "\n------------\n"
  val s2 = "\n============\n"

  def f(input: String) = 
    input.split(s2).map {
      _.split(s1) match { 
        case Array(a, b) => excpect(a, b) 
        case s => ZIO.fail(Error.BadTestData())
      }
    }

  def testFile(filename: String) = {
    for {
      input <- ZIO.readFile(filename).mapError(_ => Error.FileNotFound(filename))
      test   = Chunk.fromArray(f(input))
      _     <- test.mapZIODiscard(x => x) //ZIO.foreach(test)
    } yield ()
  }

  def testAll: IO[Error,Unit] = 
    Chunk.range(0, 10)
      .map(i => s"data/${solution.day}.$i")
      .mapZIODiscard(testFile(_).ignore)
}

package layer {
  object Utils {
    def live = ZLayer { ZIO.service[Solution].map(UtilsLive(_)) }
  }
}
