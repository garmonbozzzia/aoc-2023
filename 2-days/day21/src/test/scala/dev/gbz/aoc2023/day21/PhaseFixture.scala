package dev.gbz.aoc2023.day21

// aaaxxxyyyzzz
// aaaxxxyyyzzz
// aaaxxxyyyzzz
// xxxyyyzzz
// xxxyyyzzz
// xxxyyyzzz
// yyyzzz
// yyyzzz
// yyyzzz

object PhaseFixture {
  val fixture = Seq(
    0 -> Phase(true, 3, 0),
    1 -> Phase(false, 4, 1),
    2 -> Phase(true, 0, 2),
    3 -> Phase(false, 3, 0),
    4 -> Phase(true, 4, 1),
    5 -> Phase(false, 0, 2),
  )
}