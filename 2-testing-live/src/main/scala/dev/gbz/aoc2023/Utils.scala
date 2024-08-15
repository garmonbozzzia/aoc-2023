package dev.gbz
package aoc2023

import zio._
import TraceOps._
import Helpers._

case class TestingLive(solution: Solution) extends Testing {
  def ok(tag: String) = green(s"$tag: OK")
  
  def fail(tag: String, a: String, b: String) = red(s"$tag: Got=$a != Expected=$b")

  def expect(input: String, expectedMap: Map[String, String]) = 
    solution.test(input).flatMap { resultMap =>
      val resKeys = expectedMap.keySet - "result"
      val expKeys = resultMap.keySet - "result"

      // TODO resKeys diff expKeys
      // TODO expKeys diff resKeys

      val ba = ZIO.foreachDiscard(resKeys.intersect(expKeys)) { key =>
        val msg = 
          if(resultMap(key) == expectedMap(key)) ok(key)
          else fail(key, resultMap(key), expectedMap(key))
        Console.printLine(msg).orDie
      }
      val (ra, rb) = (resultMap("result"), expectedMap("result"))
      val resMsg = 
        if(ra == rb) ok("Result")
        else fail("Result", ra, rb)

      for {
        _ <- ba 
        _ <- Console.printLine(resMsg).orDie
        _ <- Console.printLine("="*20).orDie
      } yield ()

      // if(resultMap("result") == expectedMap("answer")) Console.printLine(ok).orDie
      // else Console.printLine(red(s"$result != $answer")).orDie
    }

  val s1 = "\n------------\n"
  val s2 = "\n============\n"

  def f(input: String) = 
    input.split(s2).map {
      _.split(s1) match { 
        case Array(a, b) => 
          val expectedMap = b.split("\n").map {
            case s"$key=$value" => (key.trim, value.trim)
            case result => ("result", result)
          }.toMap
          expect(a, expectedMap)
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
  object Testing {
    def live = ZLayer { ZIO.service[Solution].map(TestingLive(_)) }
  }
}
