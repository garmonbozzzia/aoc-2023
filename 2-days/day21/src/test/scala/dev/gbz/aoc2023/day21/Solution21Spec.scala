package dev.gbz
package aoc2023
package day21

import zio.test.{test, _}
import zio._

import TraceOps._

case class Board(s: String)

// aaaxxxyyyzzz
// aaaxxxyyyzzz
// aaaxxxyyyzzz
// xxxyyyzzz
// xxxyyyzzz
// xxxyyyzzz
// yyyzzz
// yyyzzz
// yyyzzz


case class TileSpec(tile1: Tile, tile2: Tile) {
  val vs = tile1.expansion(0, 0)
  vs.seq.size.trace
  val steps = 50
  // tile1
  val vs1 = vs.total(
    Corner.make(steps / tile1.n + 1).trace,
    Phase.make(steps, tile1.n.trace).trace,
  ).trace
  // Phase.make(6, tile1.n).trace

  val vs2 = tile2.expansion(0,0).seq(steps)

  def apply = suite("tilespec") {
    test("total equals both ways") {
      assertTrue(vs2 == vs1)
    }
  }
  
}

case class SolutionSpec(
  name: String, 
  input: String,
  expected: Map[String, String],
) {

  def ignoreIf(s: String*) = 
    if(!s.exists(expected.contains)) TestAspect.ignore
    else TestAspect.identity

  def makeTest[A](name: String)(a: => A) = {
    test(name) {
      assertTrue(a.toString == expected(name))
    } @@ ignoreIf(name)
  }

  val solution = Solution21(input)

  val test1 = test("solution.border.size") {
    assertTrue(
      input.split("\n").map(_.count(_ == '#')).sum ==
      solution.innerBorder.size
    )
  }

  val tileTest = {
    suite("tile") (
      makeTest("r6") {
        solution.expansion.seq(6)
      } @@ ignoreIf("r6"),

      makeTest("r10") {
        solution.expansion.seq(10)
      } @@ ignoreIf("r10"),
    )
  } 

  // val a = Tile.corners(input, 1).trace

  val corners = suite("corners")(
    test("fixture") {
      check(Gen.fromIterable(CornerFixture.fixture)) { case (n, corner) =>
        assertTrue(corner == Corner.makeInc(n))
      }
    },
    test("makeInc == make") {
      check(Gen.int(1,20)) { n =>
        assertTrue(Corner.make(n) == Corner.makeInc(n))
      }
    } 
  ) 

  val phaseTest = suite("phase")(
    test("fixture") {
      check(Gen.fromIterable(PhaseFixture.fixture)) { case (n, corner) =>
        assertTrue(corner == Phase.make(n, 3))
      }
    },
  )

  val expansionSize = 
    test("expansion size") {
      val tile = Tile(input, 1)
      val expansion = tile.expansion(0, 0)
      
      assertTrue(
        (tile.n * 2 - 1) == expansion.seq.size
      )
    }
    // tile.expansion(0, 0).size

  // Values(Tile)

  val multTest = test("mult") {
    val tile = Tile(input, expected("mult").toInt)
    tile.expansion(0, 0)
    assertTrue(true)
  } @@ ignoreIf("mult") @@ TestAspect.samples(1)

// x.x.x.x.
// .x.x.x..
// x.x.x...
// .x.x....
// x.x.....
// .x......
// x.......
// ........


// [1, 2, 4, 4, 5]
// 3*4 + (2 * 4) + 5 
// 4 4 5
// 
// -x.x .x. x.x ...
//  .x. x.x .x. ...
//  x.x .x. x.. ...

// -.x. x.x ... ...
//  x.x .x. ... ...
//  .x. x.. ... ...

// -x.x ... ... ...
//  .x. ... ... ...
//  x.. ... ... ...

  def mktest[A](s: String)(f: String => A)(implicit tc: TestConstructor[Any, A]) = 
    test(s)(f(expected(s))) @@ ignoreIf(s)

  def valuesTest = {
    val a = Tile(input, 1).expansion(0,0)
    val b = Tile(input, 100).expansion(0,0)
    // if(a.total(i) != b.total(i)) 
    check(Gen.int(1,100)) { case i => 
      val ai = a.total(i)
      val bi = b.total(i)
      if(ai != bi) {
        a.totalDebug(i, s"a: $ai ${ Tile(input, 1).n }")
        b.totalDebug(i, s"b: $bi ${ Tile(input, 1).n }")
      } 
      assertTrue(ai == bi)
    }
      
    // Chunk.range(2, 20, 2).map(a.total).ttrace("Tile(input, 1).expansion(0,0).total(10)")
    // Chunk.range(2, 20, 2).map(b.total).ttrace("Tile(input, 1).expansion(0,0).total(10)")
    // a.total(8).ttrace("TT")
    // b.total(8).ttrace("TT")
    
  } 

  // def aa(s: String) = {
  //   test(s) {
  //     val a = Tile(input, 1).expansion(0,0)
  //     val b = Tile(input, 10).expansion(0,0)
  //     check(Gen.int(1,20)) {
  //       case i => assertTrue(a.total(i) == b.total(i))
  //     }
  //   } @@ ignoreIf(s)

      
  //   // Chunk.range(2, 20, 2).map(a.total).ttrace("Tile(input, 1).expansion(0,0).total(10)")
  //   // Chunk.range(2, 20, 2).map(b.total).ttrace("Tile(input, 1).expansion(0,0).total(10)")
  //   // a.total(8).ttrace("TT")
  //   // b.total(8).ttrace("TT")
    
  // } 

  def tiles4 = {
    val a = Tile.corners(input, 1)
    a.map(_.trace)
    assertTrue(true)
  }

  def totalShifted(steps: String) = {
    val a = Tile(input, 1)
    val nn = (a.n / 2).trace
    val st = solution.total(steps.toInt)
    assertTrue(
      st == 
      // a.expansion(0, 0).total(steps.toInt)
      a.expansion(nn, nn).total(steps.toInt)
    )
  }

  def result = {
    val tile = Tile(input, 1).shift1

    val steps = 26501365

    // val res = Seq(
    //   tile.shift1.trace.expansion(0,0).totalDebug(steps, "1"),
    //   tile.shift2.expansion(0,0).totalDebug(steps, "2"),
    //   tile.shift3.expansion(0,0).totalDebug(steps, "3"),
    //   tile.shift4.expansion(0,0).totalDebug(steps, "4"),
    // ).sum - ((steps + 1) / 2) * 4

    def total(steps: Int) = Seq(
      tile.expansion(0, 0).totalDebug(steps, "1"),
      tile.expansion(tile.n - 1, 0).totalDebug(steps, "2"),
      tile.expansion(0, tile.n - 1).totalDebug(steps, "3"),
      tile.expansion(tile.n - 1, tile.n - 1).totalDebug(steps, "4"),
    ).sum - (steps + 1) / 2 * 4

    //  ....s.... (n + 1)

    assertTrue(solution.total(steps) == 599722156278049L) ==> 
    assertTrue(total(64) == 3003L)
  }
// 599722156278049
  //           7282802977L
  // a: (7336, 1641286758)
  // x: (7318, 1641387908)
  // y: (6466, 202300)
  // z: (857, 202301)

  def shifted = {
    val tile = Tile(input, 1)

    val t1 = tile.shift1.trace
    val t2 = tile.shift2.trace
    val t3 = tile.shift3.trace
    val t4 = tile.shift4.trace

    // Tile.corners(input, 1).map { _.shift }.map(_.trace)
    
    assertTrue(true)
  }

  def makeSuite = 
    suite(name) (
      makeTest("result1")(solution.result1) @@ TestAspect.ignore,
      makeTest("result2")(solution.result2) @@ TestAspect.ignore,
      makeTest("expansion")(Tile(input, 1).expansion(0,0).seq),
      mktest("values")(_ => valuesTest),
      mktest("4tiles")(_ => tiles4),
      mktest("shifted")(_ => shifted),
      mktest("result")(_ => result),
      mktest("totalShifted")(totalShifted) @@ TestAspect.repeats(1),
      // aa("values"),
      corners,
      // phaseTest,
      test1,
      // tileTest,
      // multTest,
      // TileSpec(Tile(input, 1), Tile(input, 5)).apply @@ ignoreIf("mult"),
      // test1
      test("square")(assertTrue(solution.board.m == solution.board.n)),
      expansionSize
    ) @@ TestAspect.timed
}

object SolutionSpec {
  val day = 21
}
