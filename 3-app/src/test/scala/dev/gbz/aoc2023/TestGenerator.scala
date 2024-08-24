package dev.gbz
package aoc2023

import zio._
import zio.test.{test, _}

case class Expect(input: String, expected: Map[String, String])

object TestGenerator {

  val sep1 = "\n------------\n"
  val sep2 = "\n============\n"

  def makeTest(input: String, expectedMap: Map[String, String]) = 
    expectedMap.map { case (k, v) =>
      test(k) {
        assertTrue { 
          val kk = k + k
          val k4 = kk + kk
          (kk + k4).size.toString == v 
        }
      } 
    }

  def parse(s: String) = 
    Chunk.fromArray(s.split(sep2))
      .mapZIO {
        _.split(sep1) match { 
          case Array(input, data) =>
            val expectedMap = data.split("\n").map {
              case s"$key=$value" => (key.trim, value.trim)
              case s"- $key:$value" => (key.trim, value.trim)
              case result         => ("result", result)
            }.toMap
            ZIO.succeed(Expect(input, expectedMap))
        }
      }
      

  def testFile(filename: String) = 
    for {
      s    <- ZIO.readFile(filename)
                .mapError(_ => Error.FileNotFound(filename))
      test <- parse(s)
      // _     <- test.mapZIODiscard(x => expect(x.input, x.expected)) //ZIO.foreach(test)
    } yield ()
  
}

// object SomeTests extends ZIOSpecDefault {
//   override def spec: Spec[TestEnvironment with Scope,Any] = 
//     suite("day17")(
//       TestGenerator.makeTest("1", Map(
//         "abc" -> "3",
//         "abcd" -> "4",
//         "abcde" -> "4"
//       ))
//     )
// }
