package dev.gbz
package aoc2023

import zio._
import zio.Console._

object MyApp extends ZIOAppDefault {

  // val  = 
  def run = myAppLogic.provide(
    day5.Solution
  )

  val myAppLogic =
    for {
      // _     <- Solution.file
      _     <- Utils.testAll//("data/3.a")
      input <- ZIO.readFile("data/3")
      result = Solution(input).result
      _     <- printLine(result)
      // t1    <-
      // testA <- ZIO.readFile("data/3.a")
      // _ <- input
      // _ <- Solution(input).expected(467835)
      // _    <- printLine("Hello! What is your name?")
      // name <- readLine
      // _    <- printLine(s"Hello, ${name}, welcome to ZIO!")
    } yield ()
}